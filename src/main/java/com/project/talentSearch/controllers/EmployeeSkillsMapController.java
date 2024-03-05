package com.project.talentSearch.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.dto.EmployeeSkillsMapDto;
import com.project.talentSearch.dto.queries.GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto;
import com.project.talentSearch.entity.EmpDetails;
import com.project.talentSearch.entity.EmployeeSkillMap;
import com.project.talentSearch.entity.Skills;
import com.project.talentSearch.repositories.EmpDetailsRepository;
import com.project.talentSearch.repositories.EmployeeSkillMapRepository;
import com.project.talentSearch.repositories.SkillsRepository;

@RestController
public class EmployeeSkillsMapController {

  @Autowired
  private SkillsRepository skillsRepository;

  @Autowired
  private EmployeeSkillMapRepository employeeSkillMapRepository;

  @Autowired
  private EmpDetailsRepository empDetailsRepository;

  private static final Logger logger = LoggerFactory.getLogger(EmployeeSkillsMapController.class);

  // Method to map EmployeeSkillMap to EmployeeSkillsMapDto
  private EmployeeSkillsMapDto toEmployeeSkillsMapDto(EmployeeSkillMap empSkillMap) {
    EmployeeSkillsMapDto dto = new EmployeeSkillsMapDto();
    dto.setId(empSkillMap.getId());
    dto.setSkillScore(empSkillMap.getSkillScore());
    return dto;
  }

  @PostMapping("/map/mapEmployeeToSkill")
  public ResponseEntity<EmployeeSkillsMapDto> mapEmployeeToSkill(@RequestBody EmployeeSkillMap empSkillMapFrom) {
    logger.info("Employee ID: " + empSkillMapFrom.getEmployee().getId());
    // Check if the employee exists
    Optional<EmpDetails> empDetailsOptional = empDetailsRepository.findById(empSkillMapFrom.getEmployee().getId());
    if (!empDetailsOptional.isPresent()) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    logger.info("Skill ID: " + empSkillMapFrom.getSkill().getId());
    // Check if the skill exists
    Optional<Skills> skillsOptional = skillsRepository.findById(empSkillMapFrom.getSkill().getId());
    if (!skillsOptional.isPresent()) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    // If both the employee and the skill exist, create a new EmployeeSkillMap
    EmployeeSkillMap newEmpSkillMap = EmployeeSkillMap.builder()
        .skillScore(empSkillMapFrom.getSkillScore())
        .employee(empDetailsOptional.get())
        .skill(skillsOptional.get())
        .build();

    // Save the new EmployeeSkillMap to the database
    newEmpSkillMap = employeeSkillMapRepository.save(newEmpSkillMap);

    // Convert the EmployeeSkillMap to EmployeeSkillsMapDto
    EmployeeSkillsMapDto dto = toEmployeeSkillsMapDto(newEmpSkillMap);

    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  private GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto toEmpWithSkillScoreMoreThan7OnGivenSkillIdDto(
      Object[] queryResult) {
    GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto dto = new GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto();
    dto.setId((Long) queryResult[0]);
    dto.setEmpName((String) queryResult[1]);
    dto.setRoleId((Long) queryResult[2]);
    dto.setRoleName((String) queryResult[3]);
    dto.setSkillId((Long) queryResult[4]);
    dto.setSkillName((String) queryResult[5]);
    dto.setSkillScore((Integer) queryResult[6]);
    return dto;
  }

  @GetMapping("/employee/getEmployeesWithSkillScoreMoreThan7/{skillId}")
  public ResponseEntity<List<GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto>> getEmployeesWithSkillScoreMoreThan7(
      @PathVariable Long skillId) {
    List<Object[]> queryResult = employeeSkillMapRepository.findEmpWithSkillScoreMoreThan7OnGivenSkillId(skillId);
    List<GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto> dtos = queryResult.stream()
        .map(this::toEmpWithSkillScoreMoreThan7OnGivenSkillIdDto)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

}

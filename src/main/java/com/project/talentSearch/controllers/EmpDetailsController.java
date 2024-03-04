package com.project.talentSearch.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.dto.EmpDetailDto;
import com.project.talentSearch.entity.EmpDetails;
import com.project.talentSearch.entity.EmployeeExperience;
import com.project.talentSearch.entity.Roles;
import com.project.talentSearch.repositories.EmpDetailsRepository;
import com.project.talentSearch.repositories.RolesRepository;

@RestController
public class EmpDetailsController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private EmpDetailsRepository empDetailsRepository;

  @Autowired
  private RolesRepository rolesRepository;

  @PostMapping("/employee/addNewEmployee")
  public ResponseEntity<EmpDetailDto> addNewEmployee(@RequestBody EmpDetails empForm) {
    if (empForm != null) {
      // Find or create the role
      Roles role = rolesRepository.findByRoleName(empForm.getRole().getRoleName());
      if (role == null) {
        role = new Roles();
        role.setRoleName(empForm.getRole().getRoleName());
        role = rolesRepository.save(role);
      }

      // Create the new employee
      EmpDetails newEmployee = EmpDetails.builder()
          .empName(empForm.getEmpName())
          .empEmail(empForm.getEmpEmail())
          .empPhone(empForm.getEmpPhone())
          .empAddress(empForm.getEmpAddress())
          .empJoiningDate(empForm.getEmpJoiningDate())
          .role(role)
          .build();

      // Associate the employee with the experiences
      if (empForm.getExperiences() != null) {
        for (EmployeeExperience experience : empForm.getExperiences()) {
          experience.setEmployee(newEmployee);
        }
        newEmployee.setExperiences(empForm.getExperiences());
      }

      newEmployee = empDetailsRepository.save(newEmployee);
      EmpDetailDto dto = modelMapper.map(newEmployee, EmpDetailDto.class);

      return new ResponseEntity<EmpDetailDto>(dto, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  private EmpDetailDto toEmpDetailDto(Object[] queryResult) {
    EmpDetailDto dto = new EmpDetailDto();
    dto.setId(((EmpDetails) queryResult[0]).getId());
    dto.setEmpName(((EmpDetails) queryResult[0]).getEmpName());
    dto.setEmpEmail(((EmpDetails) queryResult[0]).getEmpEmail());
    dto.setEmpPhone(((EmpDetails) queryResult[0]).getEmpPhone());
    dto.setEmpAddress(((EmpDetails) queryResult[0]).getEmpAddress());
    dto.setEmpJoiningDate(((EmpDetails) queryResult[0]).getEmpJoiningDate());
    return dto;
  }

  @GetMapping("/employee/worksMoreThan2Year")
  public ResponseEntity<List<EmpDetailDto>> getEmpWorksMoreThan2Year() {
    List<Object[]> queryResult = empDetailsRepository.findEmpWorkingMoreThan2Years();
    if (queryResult.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    List<EmpDetailDto> dtos = queryResult.stream()
        .map(element -> this.toEmpDetailDto(element))
        .collect(Collectors.toList());
    return new ResponseEntity<List<EmpDetailDto>>(dtos, HttpStatus.OK);

  }

}

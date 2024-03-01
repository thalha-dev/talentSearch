package com.project.talentSearch.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.dto.EmployeeProjectMapDto;
import com.project.talentSearch.entity.CompanyProjects;
import com.project.talentSearch.entity.EmpDetails;
import com.project.talentSearch.entity.EmployeeProjectMap;
import com.project.talentSearch.repositories.CompanyProjectsRepository;
import com.project.talentSearch.repositories.EmpDetailsRepository;
import com.project.talentSearch.repositories.EmployeeProjectMapRepository;

@RestController
public class EmployeeProjectMapController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private EmpDetailsRepository empDetailsRepository;

  @Autowired
  private EmployeeProjectMapRepository employeeProjectMapRepository;

  @Autowired
  private CompanyProjectsRepository companyProjectsRepository;

  @PostMapping("/map/mapEmployeeToProject")
  public ResponseEntity<EmployeeProjectMapDto> mapEmployeeToProject(@RequestBody EmployeeProjectMap empProjMapFrom) {
    // Check if the employee exists
    Optional<EmpDetails> empDetailsOptional = empDetailsRepository.findById(empProjMapFrom.getEmployee().getId());
    if (!empDetailsOptional.isPresent()) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    // Check if the project exists
    Optional<CompanyProjects> companyProjectsOptional = companyProjectsRepository
        .findById(empProjMapFrom.getProject().getId());
    if (!companyProjectsOptional.isPresent()) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    // If both the employee and the project exist, create a new EmployeeProjectMap
    EmployeeProjectMap newEmpProjMap = EmployeeProjectMap.builder()
        .currentStatus(empProjMapFrom.getCurrentStatus())
        .occupancy(empProjMapFrom.getOccupancy())
        .startDate(empProjMapFrom.getStartDate())
        .endDate(empProjMapFrom.getEndDate())
        .employee(empDetailsOptional.get())
        .project(companyProjectsOptional.get())
        .build();

    // Save the new EmployeeProjectMap to the database
    newEmpProjMap = employeeProjectMapRepository.save(newEmpProjMap);
    EmployeeProjectMapDto dto = modelMapper.map(newEmpProjMap, EmployeeProjectMapDto.class);

    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

}

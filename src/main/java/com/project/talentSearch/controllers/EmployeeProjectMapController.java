package com.project.talentSearch.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.dto.EmployeeProjectMapDto;
import com.project.talentSearch.dto.queries.GetHalfOccupiedManagersDto;
import com.project.talentSearch.dto.queries.GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto;
import com.project.talentSearch.dto.queries.GetUnOccupiedEmpDto;
import com.project.talentSearch.dto.queries.GetUnoccupiedAndHalfOccupiedManagersDto;
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

  private GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto toGetUnOccupiedEmpAndHalfOccupiedManagerNativeDto(
      Object[] queryResult) {
    GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto dto = new GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto();
    dto.setId((Long) queryResult[0]);
    dto.setEmpName((String) queryResult[1]);
    dto.setRoleId((Long) queryResult[2]);
    dto.setOccupancy((Integer) queryResult[3]);
    dto.setRoleName((String) queryResult[4]);
    return dto;
  }

  private GetUnoccupiedAndHalfOccupiedManagersDto toGetUnoccupiedAndHalfOccupiedManagersDto(Object[] queryResult) {
    GetUnoccupiedAndHalfOccupiedManagersDto dto = new GetUnoccupiedAndHalfOccupiedManagersDto();
    dto.setId(((EmpDetails) queryResult[0]).getId());
    dto.setEmpName(((EmpDetails) queryResult[0]).getEmpName());
    if (((EmpDetails) queryResult[0]).getEmpEmail() != null) {
      dto.setEmpEmail(((EmpDetails) queryResult[0]).getEmpEmail());
    }
    dto.setRoleId(((EmpDetails) queryResult[0]).getRole().getId());
    dto.setRoleName((String) queryResult[1]);
    if (queryResult.length > 2) {
      dto.setOccupancy((Integer) queryResult[2]);
    } else {
      dto.setOccupancy(null);
    }
    return dto;
  }

  private GetHalfOccupiedManagersDto toGetHalfOccupiedManagersDto(Object[] queryResult) {
    GetHalfOccupiedManagersDto dto = new GetHalfOccupiedManagersDto();
    dto.setId(((EmpDetails) queryResult[0]).getId());
    dto.setEmpName(((EmpDetails) queryResult[0]).getEmpName());
    if (((EmpDetails) queryResult[0]).getEmpEmail() != null) {
      dto.setEmpEmail(((EmpDetails) queryResult[0]).getEmpEmail());
    }
    dto.setRoleId(((EmpDetails) queryResult[0]).getRole().getId());
    dto.setRoleName((String) queryResult[1]);
    if (queryResult.length > 2) {
      dto.setOccupancy((Integer) queryResult[2]);
    } else {
      dto.setOccupancy(null);
    }
    return dto;
  }

  private GetUnOccupiedEmpDto toGetUnOccupiedEmpDto(Object[] queryResult) {
    GetUnOccupiedEmpDto dto = new GetUnOccupiedEmpDto();
    dto.setId(((EmpDetails) queryResult[0]).getId());
    dto.setEmpName(((EmpDetails) queryResult[0]).getEmpName());
    if (((EmpDetails) queryResult[0]).getEmpEmail() != null) {
      dto.setEmpEmail(((EmpDetails) queryResult[0]).getEmpEmail());
    }
    dto.setRoleId(((EmpDetails) queryResult[0]).getRole().getId());
    dto.setRoleName((String) queryResult[1]);
    if (queryResult.length > 2) {
      dto.setOccupancy((Integer) queryResult[2]);
    } else {
      dto.setOccupancy(null);
    }
    return dto;
  }

  // get the list of unoccupied employees
  @GetMapping("/employee/getUnoccupiedEmployees")
  public ResponseEntity<List<GetUnOccupiedEmpDto>> getUnoccupiedEmployees() {
    List<Object[]> queryResult = employeeProjectMapRepository.findUnoccupiedEmployees();
    List<GetUnOccupiedEmpDto> dtos = queryResult.stream()
        .map(element -> this.toGetUnOccupiedEmpDto(element))
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  // get the list of managers who occupied 50%
  @GetMapping("/employee/getHalfOccupiedManager")
  public ResponseEntity<List<GetHalfOccupiedManagersDto>> getHalfOccupiedManagers() {
    List<Object[]> queryResult = employeeProjectMapRepository.findHalfOccupiedManagers();
    List<GetHalfOccupiedManagersDto> dtos = queryResult.stream()
        .map(element -> this.toGetHalfOccupiedManagersDto(element))
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  // get the list of unoccupied employees and
  // get the list of managers who occupied 50%
  @GetMapping("/employee/getUnoccupiedEmpAndHalfOccupiedManager")
  public ResponseEntity<List<GetUnoccupiedAndHalfOccupiedManagersDto>> getUnoccupiedAndHalfOccupied() {
    List<Object[]> unoccupiedResult = employeeProjectMapRepository.findUnoccupiedEmployees();
    List<Object[]> halfOccupiedResult = employeeProjectMapRepository.findHalfOccupiedManagers();

    List<GetUnoccupiedAndHalfOccupiedManagersDto> dtos = Stream
        .concat(unoccupiedResult.stream(), halfOccupiedResult.stream())
        .map(element -> this.toGetUnoccupiedAndHalfOccupiedManagersDto(element))
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  // get the list of unoccupied employees and
  // get the list of managers who occupied 50%
  // using native mysql query
  @GetMapping("/employee/getUnoccupiedEmpAndHalfOccupiedManagerNative")
  public ResponseEntity<List<GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto>> getUnoccupiedAndHalfOccupiedNative() {
    List<Object[]> queryResult = employeeProjectMapRepository.findUnoccupiedEmployeesAndHalfOccupiedManagersNative();
    List<GetUnOccupiedEmpAndHalfOccupiedManagerNativeDto> dtos = queryResult.stream()
        .map(element -> this.toGetUnOccupiedEmpAndHalfOccupiedManagerNativeDto(element))
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

}

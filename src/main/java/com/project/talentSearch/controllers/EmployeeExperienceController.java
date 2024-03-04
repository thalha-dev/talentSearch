package com.project.talentSearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.project.talentSearch.repositories.EmployeeExperienceRepository;

@RestController
public class EmployeeExperienceController {

  @Autowired
  private EmployeeExperienceRepository employeeExperienceRepository;

  @GetMapping("/employee/totalExperience/{empId}")
  public ResponseEntity<Integer> getTotalExperience(@PathVariable Long empId) {
    Integer totalExperience = employeeExperienceRepository.getTotalExperience(empId);
    return new ResponseEntity<>(totalExperience, HttpStatus.OK);
  }

}

package com.project.talentSearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.entity.CompanyProjects;
import com.project.talentSearch.repositories.CompanyProjectsRepository;

@RestController
public class CompanyProjectsController {

  @Autowired
  private CompanyProjectsRepository companyProjectsRepository;

  @PostMapping("projects/addNewProject")
  public ResponseEntity<CompanyProjects> addNewProject(@RequestBody CompanyProjects projectForm) {
    if (projectForm != null && projectForm.getProjectName() != null && projectForm.getProjectStartDate() != null
        && projectForm.getProjectDueDate() != null && projectForm.getProjectStatus() != null) {
      CompanyProjects newProject = companyProjectsRepository.save(projectForm);
      return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

}

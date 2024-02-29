package com.project.talentSearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.entity.Roles;
import com.project.talentSearch.repositories.RolesRepository;

@RestController
public class RolesController {

  @Autowired
  private RolesRepository rolesRepository;

  @PostMapping("/role/addNewRole")
  public ResponseEntity<Roles> addNewSkill(@RequestBody Roles newRole) {
    Roles saved = rolesRepository.save(newRole);
    return new ResponseEntity<Roles>(saved, HttpStatus.CREATED);
  }

}

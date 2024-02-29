package com.project.talentSearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.talentSearch.entity.Skills;
import com.project.talentSearch.repositories.SkillsRepository;

@RestController
public class SkillsController {
  @Autowired
  private SkillsRepository skillsRepository;

  @PostMapping("/skill/addNewSkill")
  public ResponseEntity<Skills> addNewSkill(@RequestBody Skills newSkill) {
    Skills saved = skillsRepository.save(newSkill);
    return new ResponseEntity<Skills>(saved, HttpStatus.CREATED);
  }

}

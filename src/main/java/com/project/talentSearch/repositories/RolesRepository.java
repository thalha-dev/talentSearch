package com.project.talentSearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.talentSearch.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

  Roles findByRoleName(String roleName);

}

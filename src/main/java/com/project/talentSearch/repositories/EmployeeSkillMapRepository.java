package com.project.talentSearch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.talentSearch.entity.EmployeeSkillMap;

public interface EmployeeSkillMapRepository extends JpaRepository<EmployeeSkillMap, Long> {

  @Query("SELECT e.id, e.empName, e.role.id, r.roleName, map.skill.id, sk.skillName, map.skillScore " +
      "FROM EmpDetails e " +
      "INNER JOIN Roles r ON e.role.id = r.id " +
      "LEFT JOIN EmployeeSkillMap map ON e.id = map.employee.id " +
      "LEFT JOIN Skills sk ON map.skill.id = sk.id " +
      "WHERE sk.id = :skillId and map.skillScore > 7")
  List<Object[]> findEmpWithSkillScoreMoreThan7OnGivenSkillId(@Param("skillId") Long skillId);

}

package com.project.talentSearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.talentSearch.entity.EmployeeExperience;

public interface EmployeeExperienceRepository extends JpaRepository<EmployeeExperience, Long> {

  @Query(value = "SELECT SUM(tb.temp) AS total_exp_year " +
      "FROM ( " +
      "SELECT SUM(ex.years_of_experience) AS temp " +
      "FROM employee_experience AS ex " +
      "WHERE ex.emp_id = :empId " +
      "UNION " +
      "SELECT TIMESTAMPDIFF(YEAR, emp.emp_joining_date, CURDATE()) as temp " +
      "FROM emp_details AS emp " +
      "WHERE emp.emp_id = :empId " +
      ") AS tb", nativeQuery = true)
  Integer getTotalExperience(@Param("empId") Long empId);

}

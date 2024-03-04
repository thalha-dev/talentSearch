package com.project.talentSearch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.talentSearch.entity.EmpDetails;

public interface EmpDetailsRepository extends JpaRepository<EmpDetails, Long> {

  @Query("SELECT e FROM EmpDetails e WHERE YEAR(CURRENT_DATE) - YEAR(e.empJoiningDate) > 2")
  List<Object[]> findEmpWorkingMoreThan2Years();

}

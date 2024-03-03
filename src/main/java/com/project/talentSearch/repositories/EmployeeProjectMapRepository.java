package com.project.talentSearch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.talentSearch.entity.EmployeeProjectMap;

public interface EmployeeProjectMapRepository extends JpaRepository<EmployeeProjectMap, Long> {

  @Query("SELECT DISTINCT e, r.roleName, map.occupancy " +
      "FROM EmpDetails e " +
      "INNER JOIN EmployeeProjectMap map ON e.id = map.employee.id " +
      "INNER JOIN Roles r ON e.role.id = r.id " +
      "WHERE map.occupancy = 0 AND e.id NOT IN (" +
      "SELECT emp.id FROM EmpDetails emp " +
      "INNER JOIN EmployeeProjectMap s ON emp.id = s.employee.id " +
      "WHERE s.occupancy != 0)")
  List<Object[]> findUnoccupiedEmployees();

  @Query("SELECT DISTINCT e, r.roleName, map.occupancy " +
      "FROM EmpDetails e " +
      "INNER JOIN EmployeeProjectMap map ON e.id = map.employee.id " +
      "INNER JOIN Roles r ON e.role.id = r.id " +
      "WHERE map.occupancy = 50 AND r.roleName = 'Manager'")
  List<Object[]> findHalfOccupiedManagers();

  @Query(value = "SELECT e.*, r.role_name FROM (" +
      "SELECT DISTINCT emp.emp_id, emp.emp_name, emp.role_id, map.occupancy " +
      "FROM talent_search.emp_details AS emp " +
      "INNER JOIN (" +
          "SELECT * " +
          "FROM talent_search.employee_project_map AS s " +
          "WHERE s.occupancy = 0" +
      ") AS map ON emp.emp_id = map.emp_id " +
      "WHERE emp.emp_id NOT IN (" +
          "SELECT emp_id " +
          "FROM talent_search.employee_project_map AS s " +
          "WHERE s.occupancy != 0" +
      ")" +
      ") AS e inner join talent_search.roles AS r " +
      "ON e.role_id = r.role_id " +
      "UNION " +
      "SELECT e.*, r.role_name FROM (" +
          "SELECT DISTINCT emp.emp_id, emp.emp_name, emp.role_id, map.occupancy " +
          "FROM talent_search.emp_details AS emp " +
          "INNER JOIN (" +
              "SELECT * " +
              "FROM talent_search.employee_project_map AS s " +
              "WHERE s.occupancy = 50" +
          ") AS map ON emp.emp_id = map.emp_id " +
      ") AS e inner join talent_search.roles AS r " +
      "ON e.role_id = r.role_id " +
      "WHERE r.role_name='Manager'", nativeQuery = true)
  List<Object[]> findUnoccupiedEmployeesAndHalfOccupiedManagersNative();

  @Query("SELECT m, e.empName " +
       "FROM EmployeeProjectMap m " +
       "JOIN m.project p " +
       "JOIN m.employee e " +
       "WHERE m.employee.id = :empId")
  List<Object[]> findEmployeeProjectDetails(@Param("empId") Long empId);

}

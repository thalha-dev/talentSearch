package com.project.talentSearch.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "employee_project_map")
public class EmployeeProjectMap {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "epmloyee_project_map_id")
  private Long Id;

  /*
   * Assigned: The employee has been assigned to the project but has not started
   * work yet.
   * Active: The employee is currently working on the project.
   * OnHold: Work by the employee on the project has been temporarily paused.
   * Completed: The employee has finished all their tasks for the project.
   * Released: The employee was part of the project but has now been released from
   * it.
   */
  @Column(name = "current_status")
  private String currentStatus;

  @Column(name = "occupancy")
  private Integer occupancy;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmpDetails employee;

  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  private CompanyProjects project;
}

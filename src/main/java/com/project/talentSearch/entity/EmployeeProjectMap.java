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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "employee_project_map")
public class EmployeeProjectMap {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "epmloyee_project_map_id")
  private Long Id;

  @Column(name = "current_status")
  private Integer currentStatus;

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

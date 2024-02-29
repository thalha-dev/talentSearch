package com.project.talentSearch.entity;

import java.sql.Date;

// import org.hibernate.annotations.Formula;

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
@Table(name = "employee_experience")
public class EmployeeExperience {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "experience_id")
  private Long Id;

  // @Formula("TIMESTAMPDIFF(YEAR, start_date, end_date)")
  @Column(name = "years_of_experience")
  private Integer yearsOfExperience;

  @Column(name = "employee_designation")
  private String employeeDesignation;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmpDetails employee;
}

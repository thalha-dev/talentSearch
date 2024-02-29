package com.project.talentSearch.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "emp_details", uniqueConstraints = @UniqueConstraint(name = "emp_email_unique", columnNames = "emp_email"))
public class EmpDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_id")
  private Long Id;

  @Column(name = "emp_name")
  private String empName;

  @Column(name = "emp_email")
  private String empEmail;

  @Column(name = "emp_phone")
  private Long empPhone;

  @Column(name = "emp_address")
  private String empAddress;

  @Column(name = "emp_joining_date")
  private Date empJoiningDate;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EmployeeExperience> experiences;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private Roles role;

  @OneToMany(mappedBy = "employee")
  private List<EmployeeProjectMap> projects;

  @OneToMany(mappedBy = "employee")
  private List<EmployeeSkillMap> skill;
}

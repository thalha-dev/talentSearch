package com.project.talentSearch.entity;

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
@Table(name = "employee_skill_map")
public class EmployeeSkillMap {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "epmloyee_skill_map_id")
  private Long Id;

  @Column(name = "skill_score")
  private Integer skillScore;

  @ManyToOne
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmpDetails employee;

  @ManyToOne
  @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
  private Skills skill;
}

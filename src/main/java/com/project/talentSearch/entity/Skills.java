package com.project.talentSearch.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "skills", uniqueConstraints = @UniqueConstraint(name = "skill_name_unique", columnNames = "skill_name"))
public class Skills {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "skill_id")
  private Long Id;

  @Column(name = "skill_name")
  private String skillName;

  @OneToMany(mappedBy = "skill")
  private List<EmployeeSkillMap> employee;
}

package com.project.talentSearch.entity;

import java.sql.Date;
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
@Table(name = "company_projects", uniqueConstraints = @UniqueConstraint(name = "project_name_unique", columnNames = "project_name"))
public class CompanyProjects {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "project_id")
  private Long Id;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "project_start_date")
  private Date projectStartDate;

  @Column(name = "project_due_date")
  private Date projectDueDate;

  // New: The project is in the early stages of development or planning.
  // InProgress: Work is currently being done on the project.
  // Completed: All work on the project has been finished.
  // Upcoming: The project is scheduled to be started in the near future.
  // Pending: The project has begun but has not yet been completed and is awaiting
  // -further action or approval.
  // Overdue: The project has passed its completion date and is behind schedule.
  // OnHold: Work on the project has been temporarily stopped.
  // Canceled: The project ended before completion.
  @Column(name = "project_status")
  private String projectStatus;

  @OneToMany(mappedBy = "project")
  private List<EmployeeProjectMap> employees;
}

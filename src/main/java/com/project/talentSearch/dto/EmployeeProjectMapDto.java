package com.project.talentSearch.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeProjectMapDto {
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
  private String currentStatus;

  private Integer occupancy;

  private Date startDate;

  private Date endDate;

}

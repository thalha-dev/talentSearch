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

  // 0: Not occupied
  // 1 : occupied
  private Integer currentStatus;

  private Integer occupancy;

  private Date startDate;

  private Date endDate;

}

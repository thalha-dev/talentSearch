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
public class EmpDetailDto {
  private Long Id;

  private String empName;

  private String empEmail;

  private Long empPhone;

  private String empAddress;

  private Date empJoiningDate;
}

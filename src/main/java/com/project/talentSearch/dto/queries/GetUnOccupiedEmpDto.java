package com.project.talentSearch.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUnOccupiedEmpDto {
  private Long id;
  private String empName;
  private String empEmail;
  private String roleName;
  private Long roleId;
  private Integer occupancy;
}

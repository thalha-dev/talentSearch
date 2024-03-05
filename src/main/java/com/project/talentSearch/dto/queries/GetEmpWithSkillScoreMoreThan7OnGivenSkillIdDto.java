package com.project.talentSearch.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmpWithSkillScoreMoreThan7OnGivenSkillIdDto {
  private Long id;
  private String empName;
  private Long roleId;
  private String roleName;
  private Long skillId;
  private String skillName;
  private Integer skillScore;
}

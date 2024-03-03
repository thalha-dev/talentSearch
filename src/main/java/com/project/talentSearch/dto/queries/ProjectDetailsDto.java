package com.project.talentSearch.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailsDto {
  private Long projectId;
  private String projectName;
  private Date projectStartDate;
  private Date projectDueDate;
  private String projectStatus;
}

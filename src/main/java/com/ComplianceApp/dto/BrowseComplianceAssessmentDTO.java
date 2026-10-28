package com.ComplianceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseComplianceAssessmentDTO {

	private Integer ownerId;

	private Integer complianceAssessmentId;

	private Integer complianceAssessmentStatus;
	
	private Integer nextOrPrevious;
}


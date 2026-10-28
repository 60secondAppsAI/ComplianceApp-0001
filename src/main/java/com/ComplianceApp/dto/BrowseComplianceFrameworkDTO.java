package com.ComplianceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseComplianceFrameworkDTO {

	private Integer ownerId;

	private Integer complianceFrameworkId;

	private Integer complianceFrameworkStatus;
	
	private Integer nextOrPrevious;
}


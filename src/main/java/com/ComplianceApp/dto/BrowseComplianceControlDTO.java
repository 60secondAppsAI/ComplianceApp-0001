package com.ComplianceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseComplianceControlDTO {

	private Integer ownerId;

	private Integer complianceControlId;

	private Integer complianceControlStatus;
	
	private Integer nextOrPrevious;
}


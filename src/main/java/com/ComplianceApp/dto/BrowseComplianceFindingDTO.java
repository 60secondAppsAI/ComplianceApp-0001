package com.ComplianceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseComplianceFindingDTO {

	private Integer ownerId;

	private Integer complianceFindingId;

	private Integer complianceFindingStatus;
	
	private Integer nextOrPrevious;
}


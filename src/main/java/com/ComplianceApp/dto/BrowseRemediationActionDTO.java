package com.ComplianceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRemediationActionDTO {

	private Integer ownerId;

	private Integer remediationActionId;

	private Integer remediationActionStatus;
	
	private Integer nextOrPrevious;
}


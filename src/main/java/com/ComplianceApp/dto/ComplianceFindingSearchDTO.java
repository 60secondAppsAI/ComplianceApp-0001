package com.ComplianceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplianceFindingSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer complianceFindingId;
	
	private Integer complianceAssessmentId;
	
	private Integer complianceControlId;
	
	private String severity;
	
	private String description;
	
	private String status;
	
}

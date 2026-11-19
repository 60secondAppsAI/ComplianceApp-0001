package com.ComplianceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplianceAssessmentSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer complianceAssessmentId;
	
	private Integer complianceFrameworkId;
	
	private Date assessmentDate;
	
	private String assessor;
	
	private String status;
	
}

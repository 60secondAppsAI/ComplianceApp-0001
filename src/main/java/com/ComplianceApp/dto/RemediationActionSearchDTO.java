package com.ComplianceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RemediationActionSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer remediationActionId;
	
	private Integer complianceFindingId;
	
	private String description;
	
	private String owner;
	
	private Date dueDate;
	
	private String status;
	
}

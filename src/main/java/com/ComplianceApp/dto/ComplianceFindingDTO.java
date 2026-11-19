package com.ComplianceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ComplianceFindingDTO {

	private Integer complianceFindingId;

	private Integer complianceAssessmentId;

	private Integer complianceControlId;

	private String severity;

	private String description;

	private String status;






}

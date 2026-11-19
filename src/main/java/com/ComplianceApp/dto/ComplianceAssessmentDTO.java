package com.ComplianceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ComplianceAssessmentDTO {

	private Integer complianceAssessmentId;

	private Integer complianceFrameworkId;

	private Date assessmentDate;

	private String assessor;

	private String status;






}

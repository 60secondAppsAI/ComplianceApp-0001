package com.ComplianceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RemediationActionDTO {

	private Integer remediationActionId;

	private Integer complianceFindingId;

	private String description;

	private String owner;

	private Date dueDate;

	private String status;






}

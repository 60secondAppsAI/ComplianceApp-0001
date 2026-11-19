package com.ComplianceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ComplianceControlDTO {

	private Integer complianceControlId;

	private Integer complianceFrameworkId;

	private String controlCode;

	private String title;

	private String description;






}

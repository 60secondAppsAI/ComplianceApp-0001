package com.ComplianceApp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplianceFindingPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<ComplianceFindingDTO> complianceFindings;
}






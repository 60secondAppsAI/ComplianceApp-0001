package com.ComplianceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ComplianceApp.domain.ComplianceFinding;
import com.ComplianceApp.dto.ComplianceFindingDTO;
import com.ComplianceApp.dto.ComplianceFindingSearchDTO;
import com.ComplianceApp.dto.ComplianceFindingPageDTO;
import com.ComplianceApp.dto.ComplianceFindingConvertCriteriaDTO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplianceFindingService extends GenericService<ComplianceFinding, Integer> {

	List<ComplianceFinding> findAll();

	ResultDTO addComplianceFinding(ComplianceFindingDTO complianceFindingDTO, RequestDTO requestDTO);

	ResultDTO updateComplianceFinding(ComplianceFindingDTO complianceFindingDTO, RequestDTO requestDTO);

    Page<ComplianceFinding> getAllComplianceFindings(Pageable pageable);

    Page<ComplianceFinding> getAllComplianceFindings(Specification<ComplianceFinding> spec, Pageable pageable);

	ResponseEntity<ComplianceFindingPageDTO> getComplianceFindings(ComplianceFindingSearchDTO complianceFindingSearchDTO);
	
	List<ComplianceFindingDTO> convertComplianceFindingsToComplianceFindingDTOs(List<ComplianceFinding> complianceFindings, ComplianceFindingConvertCriteriaDTO convertCriteria);

	ComplianceFindingDTO getComplianceFindingDTOById(Integer complianceFindingId);



	
}

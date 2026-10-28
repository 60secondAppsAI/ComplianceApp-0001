package com.ComplianceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ComplianceApp.domain.ComplianceAssessment;
import com.ComplianceApp.dto.ComplianceAssessmentDTO;
import com.ComplianceApp.dto.ComplianceAssessmentSearchDTO;
import com.ComplianceApp.dto.ComplianceAssessmentPageDTO;
import com.ComplianceApp.dto.ComplianceAssessmentConvertCriteriaDTO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplianceAssessmentService extends GenericService<ComplianceAssessment, Integer> {

	List<ComplianceAssessment> findAll();

	ResultDTO addComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO, RequestDTO requestDTO);

	ResultDTO updateComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO, RequestDTO requestDTO);

    Page<ComplianceAssessment> getAllComplianceAssessments(Pageable pageable);

    Page<ComplianceAssessment> getAllComplianceAssessments(Specification<ComplianceAssessment> spec, Pageable pageable);

	ResponseEntity<ComplianceAssessmentPageDTO> getComplianceAssessments(ComplianceAssessmentSearchDTO complianceAssessmentSearchDTO);
	
	List<ComplianceAssessmentDTO> convertComplianceAssessmentsToComplianceAssessmentDTOs(List<ComplianceAssessment> complianceAssessments, ComplianceAssessmentConvertCriteriaDTO convertCriteria);

	ComplianceAssessmentDTO getComplianceAssessmentDTOById(Integer complianceAssessmentId);



	
}

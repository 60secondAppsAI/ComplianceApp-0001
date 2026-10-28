package com.ComplianceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ComplianceApp.domain.ComplianceFramework;
import com.ComplianceApp.dto.ComplianceFrameworkDTO;
import com.ComplianceApp.dto.ComplianceFrameworkSearchDTO;
import com.ComplianceApp.dto.ComplianceFrameworkPageDTO;
import com.ComplianceApp.dto.ComplianceFrameworkConvertCriteriaDTO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplianceFrameworkService extends GenericService<ComplianceFramework, Integer> {

	List<ComplianceFramework> findAll();

	ResultDTO addComplianceFramework(ComplianceFrameworkDTO complianceFrameworkDTO, RequestDTO requestDTO);

	ResultDTO updateComplianceFramework(ComplianceFrameworkDTO complianceFrameworkDTO, RequestDTO requestDTO);

    Page<ComplianceFramework> getAllComplianceFrameworks(Pageable pageable);

    Page<ComplianceFramework> getAllComplianceFrameworks(Specification<ComplianceFramework> spec, Pageable pageable);

	ResponseEntity<ComplianceFrameworkPageDTO> getComplianceFrameworks(ComplianceFrameworkSearchDTO complianceFrameworkSearchDTO);
	
	List<ComplianceFrameworkDTO> convertComplianceFrameworksToComplianceFrameworkDTOs(List<ComplianceFramework> complianceFrameworks, ComplianceFrameworkConvertCriteriaDTO convertCriteria);

	ComplianceFrameworkDTO getComplianceFrameworkDTOById(Integer complianceFrameworkId);



	
}

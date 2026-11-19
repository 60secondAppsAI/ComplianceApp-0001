package com.ComplianceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ComplianceApp.domain.ComplianceControl;
import com.ComplianceApp.dto.ComplianceControlDTO;
import com.ComplianceApp.dto.ComplianceControlSearchDTO;
import com.ComplianceApp.dto.ComplianceControlPageDTO;
import com.ComplianceApp.dto.ComplianceControlConvertCriteriaDTO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplianceControlService extends GenericService<ComplianceControl, Integer> {

	List<ComplianceControl> findAll();

	ResultDTO addComplianceControl(ComplianceControlDTO complianceControlDTO, RequestDTO requestDTO);

	ResultDTO updateComplianceControl(ComplianceControlDTO complianceControlDTO, RequestDTO requestDTO);

    Page<ComplianceControl> getAllComplianceControls(Pageable pageable);

    Page<ComplianceControl> getAllComplianceControls(Specification<ComplianceControl> spec, Pageable pageable);

	ResponseEntity<ComplianceControlPageDTO> getComplianceControls(ComplianceControlSearchDTO complianceControlSearchDTO);
	
	List<ComplianceControlDTO> convertComplianceControlsToComplianceControlDTOs(List<ComplianceControl> complianceControls, ComplianceControlConvertCriteriaDTO convertCriteria);

	ComplianceControlDTO getComplianceControlDTOById(Integer complianceControlId);



	
}

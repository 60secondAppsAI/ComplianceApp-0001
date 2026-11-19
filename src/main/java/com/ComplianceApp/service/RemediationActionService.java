package com.ComplianceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ComplianceApp.domain.RemediationAction;
import com.ComplianceApp.dto.RemediationActionDTO;
import com.ComplianceApp.dto.RemediationActionSearchDTO;
import com.ComplianceApp.dto.RemediationActionPageDTO;
import com.ComplianceApp.dto.RemediationActionConvertCriteriaDTO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RemediationActionService extends GenericService<RemediationAction, Integer> {

	List<RemediationAction> findAll();

	ResultDTO addRemediationAction(RemediationActionDTO remediationActionDTO, RequestDTO requestDTO);

	ResultDTO updateRemediationAction(RemediationActionDTO remediationActionDTO, RequestDTO requestDTO);

    Page<RemediationAction> getAllRemediationActions(Pageable pageable);

    Page<RemediationAction> getAllRemediationActions(Specification<RemediationAction> spec, Pageable pageable);

	ResponseEntity<RemediationActionPageDTO> getRemediationActions(RemediationActionSearchDTO remediationActionSearchDTO);
	
	List<RemediationActionDTO> convertRemediationActionsToRemediationActionDTOs(List<RemediationAction> remediationActions, RemediationActionConvertCriteriaDTO convertCriteria);

	RemediationActionDTO getRemediationActionDTOById(Integer remediationActionId);



	
}

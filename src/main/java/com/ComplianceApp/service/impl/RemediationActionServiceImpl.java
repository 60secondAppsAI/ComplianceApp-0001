package com.ComplianceApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.ComplianceApp.dao.GenericDAO;
import com.ComplianceApp.service.GenericService;
import com.ComplianceApp.service.impl.GenericServiceImpl;
import com.ComplianceApp.dao.RemediationActionDAO;
import com.ComplianceApp.domain.RemediationAction;
import com.ComplianceApp.dto.RemediationActionDTO;
import com.ComplianceApp.dto.RemediationActionSearchDTO;
import com.ComplianceApp.dto.RemediationActionPageDTO;
import com.ComplianceApp.dto.RemediationActionConvertCriteriaDTO;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import com.ComplianceApp.service.RemediationActionService;
import com.ComplianceApp.util.ControllerUtils;


@Service
public class RemediationActionServiceImpl extends GenericServiceImpl<RemediationAction, Integer> implements RemediationActionService {

    private final static Logger logger = LoggerFactory.getLogger(RemediationActionServiceImpl.class);

	@Autowired
	RemediationActionDAO remediationActionDao;

	

	@Override
	public GenericDAO<RemediationAction, Integer> getDAO() {
		return (GenericDAO<RemediationAction, Integer>) remediationActionDao;
	}
	
	public List<RemediationAction> findAll () {
		List<RemediationAction> remediationActions = remediationActionDao.findAll();
		
		return remediationActions;	
		
	}

	public ResultDTO addRemediationAction(RemediationActionDTO remediationActionDTO, RequestDTO requestDTO) {

		RemediationAction remediationAction = new RemediationAction();

		remediationAction.setRemediationActionId(remediationActionDTO.getRemediationActionId());

		remediationAction.setComplianceFindingId(remediationActionDTO.getComplianceFindingId());

		remediationAction.setDescription(remediationActionDTO.getDescription());

		remediationAction.setOwner(remediationActionDTO.getOwner());

		remediationAction.setDueDate(remediationActionDTO.getDueDate());

		remediationAction.setStatus(remediationActionDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		remediationAction = remediationActionDao.save(remediationAction);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RemediationAction> getAllRemediationActions(Pageable pageable) {
		return remediationActionDao.findAll(pageable);
	}

	public Page<RemediationAction> getAllRemediationActions(Specification<RemediationAction> spec, Pageable pageable) {
		return remediationActionDao.findAll(spec, pageable);
	}

	public ResponseEntity<RemediationActionPageDTO> getRemediationActions(RemediationActionSearchDTO remediationActionSearchDTO) {
	
			Integer remediationActionId = remediationActionSearchDTO.getRemediationActionId(); 
 			Integer complianceFindingId = remediationActionSearchDTO.getComplianceFindingId(); 
 			String description = remediationActionSearchDTO.getDescription(); 
 			String owner = remediationActionSearchDTO.getOwner(); 
 			Date dueDate = remediationActionSearchDTO.getDueDate();
 			String status = remediationActionSearchDTO.getStatus(); 
 			String sortBy = remediationActionSearchDTO.getSortBy();
			String sortOrder = remediationActionSearchDTO.getSortOrder();
			String searchQuery = remediationActionSearchDTO.getSearchQuery();
			Integer page = remediationActionSearchDTO.getPage();
			Integer size = remediationActionSearchDTO.getSize();

	        Specification<RemediationAction> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, remediationActionId, "remediationActionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, complianceFindingId, "complianceFindingId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, owner, "owner"); 
			
			spec = ControllerUtils.andIfNecessary(spec, dueDate, "dueDate");
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("owner")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<RemediationAction> remediationActions = this.getAllRemediationActions(spec, pageable);
		
		//System.out.println(String.valueOf(remediationActions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(remediationActions.getTotalPages()));
		
		List<RemediationAction> remediationActionsList = remediationActions.getContent();
		
		RemediationActionConvertCriteriaDTO convertCriteria = new RemediationActionConvertCriteriaDTO();
		List<RemediationActionDTO> remediationActionDTOs = this.convertRemediationActionsToRemediationActionDTOs(remediationActionsList,convertCriteria);
		
		RemediationActionPageDTO remediationActionPageDTO = new RemediationActionPageDTO();
		remediationActionPageDTO.setRemediationActions(remediationActionDTOs);
		remediationActionPageDTO.setTotalElements(remediationActions.getTotalElements());
		return ResponseEntity.ok(remediationActionPageDTO);
	}

	public List<RemediationActionDTO> convertRemediationActionsToRemediationActionDTOs(List<RemediationAction> remediationActions, RemediationActionConvertCriteriaDTO convertCriteria) {
		
		List<RemediationActionDTO> remediationActionDTOs = new ArrayList<RemediationActionDTO>();
		
		for (RemediationAction remediationAction : remediationActions) {
			remediationActionDTOs.add(convertRemediationActionToRemediationActionDTO(remediationAction,convertCriteria));
		}
		
		return remediationActionDTOs;

	}
	
	public RemediationActionDTO convertRemediationActionToRemediationActionDTO(RemediationAction remediationAction, RemediationActionConvertCriteriaDTO convertCriteria) {
		
		RemediationActionDTO remediationActionDTO = new RemediationActionDTO();

		remediationActionDTO.setRemediationActionId(remediationAction.getRemediationActionId());

		remediationActionDTO.setComplianceFindingId(remediationAction.getComplianceFindingId());

		remediationActionDTO.setDescription(remediationAction.getDescription());

		remediationActionDTO.setOwner(remediationAction.getOwner());

		remediationActionDTO.setDueDate(remediationAction.getDueDate());

		remediationActionDTO.setStatus(remediationAction.getStatus());
		
		return remediationActionDTO;
	}

	public ResultDTO updateRemediationAction(RemediationActionDTO remediationActionDTO, RequestDTO requestDTO) {
		
		RemediationAction remediationAction = remediationActionDao.getById(remediationActionDTO.getRemediationActionId());
		
		remediationAction.setRemediationActionId(ControllerUtils.setValue(remediationAction.getRemediationActionId(), remediationActionDTO.getRemediationActionId()));
		
		remediationAction.setComplianceFindingId(ControllerUtils.setValue(remediationAction.getComplianceFindingId(), remediationActionDTO.getComplianceFindingId()));
		
		remediationAction.setDescription(ControllerUtils.setValue(remediationAction.getDescription(), remediationActionDTO.getDescription()));
		
		remediationAction.setOwner(ControllerUtils.setValue(remediationAction.getOwner(), remediationActionDTO.getOwner()));
		
		remediationAction.setDueDate(ControllerUtils.setValue(remediationAction.getDueDate(), remediationActionDTO.getDueDate()));
		
		remediationAction.setStatus(ControllerUtils.setValue(remediationAction.getStatus(), remediationActionDTO.getStatus()));

        remediationAction = remediationActionDao.save(remediationAction);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RemediationActionDTO getRemediationActionDTOById(Integer remediationActionId) {
	
		RemediationAction remediationAction = remediationActionDao.getById(remediationActionId);
		
		RemediationActionConvertCriteriaDTO convertCriteria = new RemediationActionConvertCriteriaDTO();
		return(this.convertRemediationActionToRemediationActionDTO(remediationAction,convertCriteria));
	}

}

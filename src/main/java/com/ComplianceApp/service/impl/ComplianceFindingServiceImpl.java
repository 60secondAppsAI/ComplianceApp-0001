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
import com.ComplianceApp.dao.ComplianceFindingDAO;
import com.ComplianceApp.domain.ComplianceFinding;
import com.ComplianceApp.dto.ComplianceFindingDTO;
import com.ComplianceApp.dto.ComplianceFindingSearchDTO;
import com.ComplianceApp.dto.ComplianceFindingPageDTO;
import com.ComplianceApp.dto.ComplianceFindingConvertCriteriaDTO;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import com.ComplianceApp.service.ComplianceFindingService;
import com.ComplianceApp.util.ControllerUtils;


@Service
public class ComplianceFindingServiceImpl extends GenericServiceImpl<ComplianceFinding, Integer> implements ComplianceFindingService {

    private final static Logger logger = LoggerFactory.getLogger(ComplianceFindingServiceImpl.class);

	@Autowired
	ComplianceFindingDAO complianceFindingDao;

	

	@Override
	public GenericDAO<ComplianceFinding, Integer> getDAO() {
		return (GenericDAO<ComplianceFinding, Integer>) complianceFindingDao;
	}
	
	public List<ComplianceFinding> findAll () {
		List<ComplianceFinding> complianceFindings = complianceFindingDao.findAll();
		
		return complianceFindings;	
		
	}

	public ResultDTO addComplianceFinding(ComplianceFindingDTO complianceFindingDTO, RequestDTO requestDTO) {

		ComplianceFinding complianceFinding = new ComplianceFinding();

		complianceFinding.setComplianceFindingId(complianceFindingDTO.getComplianceFindingId());

		complianceFinding.setComplianceAssessmentId(complianceFindingDTO.getComplianceAssessmentId());

		complianceFinding.setComplianceControlId(complianceFindingDTO.getComplianceControlId());

		complianceFinding.setSeverity(complianceFindingDTO.getSeverity());

		complianceFinding.setDescription(complianceFindingDTO.getDescription());

		complianceFinding.setStatus(complianceFindingDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		complianceFinding = complianceFindingDao.save(complianceFinding);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ComplianceFinding> getAllComplianceFindings(Pageable pageable) {
		return complianceFindingDao.findAll(pageable);
	}

	public Page<ComplianceFinding> getAllComplianceFindings(Specification<ComplianceFinding> spec, Pageable pageable) {
		return complianceFindingDao.findAll(spec, pageable);
	}

	public ResponseEntity<ComplianceFindingPageDTO> getComplianceFindings(ComplianceFindingSearchDTO complianceFindingSearchDTO) {
	
			Integer complianceFindingId = complianceFindingSearchDTO.getComplianceFindingId(); 
 			Integer complianceAssessmentId = complianceFindingSearchDTO.getComplianceAssessmentId(); 
 			Integer complianceControlId = complianceFindingSearchDTO.getComplianceControlId(); 
 			String severity = complianceFindingSearchDTO.getSeverity(); 
 			String description = complianceFindingSearchDTO.getDescription(); 
 			String status = complianceFindingSearchDTO.getStatus(); 
 			String sortBy = complianceFindingSearchDTO.getSortBy();
			String sortOrder = complianceFindingSearchDTO.getSortOrder();
			String searchQuery = complianceFindingSearchDTO.getSearchQuery();
			Integer page = complianceFindingSearchDTO.getPage();
			Integer size = complianceFindingSearchDTO.getSize();

	        Specification<ComplianceFinding> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, complianceFindingId, "complianceFindingId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, complianceAssessmentId, "complianceAssessmentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, complianceControlId, "complianceControlId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, severity, "severity"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("severity")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ComplianceFinding> complianceFindings = this.getAllComplianceFindings(spec, pageable);
		
		//System.out.println(String.valueOf(complianceFindings.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(complianceFindings.getTotalPages()));
		
		List<ComplianceFinding> complianceFindingsList = complianceFindings.getContent();
		
		ComplianceFindingConvertCriteriaDTO convertCriteria = new ComplianceFindingConvertCriteriaDTO();
		List<ComplianceFindingDTO> complianceFindingDTOs = this.convertComplianceFindingsToComplianceFindingDTOs(complianceFindingsList,convertCriteria);
		
		ComplianceFindingPageDTO complianceFindingPageDTO = new ComplianceFindingPageDTO();
		complianceFindingPageDTO.setComplianceFindings(complianceFindingDTOs);
		complianceFindingPageDTO.setTotalElements(complianceFindings.getTotalElements());
		return ResponseEntity.ok(complianceFindingPageDTO);
	}

	public List<ComplianceFindingDTO> convertComplianceFindingsToComplianceFindingDTOs(List<ComplianceFinding> complianceFindings, ComplianceFindingConvertCriteriaDTO convertCriteria) {
		
		List<ComplianceFindingDTO> complianceFindingDTOs = new ArrayList<ComplianceFindingDTO>();
		
		for (ComplianceFinding complianceFinding : complianceFindings) {
			complianceFindingDTOs.add(convertComplianceFindingToComplianceFindingDTO(complianceFinding,convertCriteria));
		}
		
		return complianceFindingDTOs;

	}
	
	public ComplianceFindingDTO convertComplianceFindingToComplianceFindingDTO(ComplianceFinding complianceFinding, ComplianceFindingConvertCriteriaDTO convertCriteria) {
		
		ComplianceFindingDTO complianceFindingDTO = new ComplianceFindingDTO();

		complianceFindingDTO.setComplianceFindingId(complianceFinding.getComplianceFindingId());

		complianceFindingDTO.setComplianceAssessmentId(complianceFinding.getComplianceAssessmentId());

		complianceFindingDTO.setComplianceControlId(complianceFinding.getComplianceControlId());

		complianceFindingDTO.setSeverity(complianceFinding.getSeverity());

		complianceFindingDTO.setDescription(complianceFinding.getDescription());

		complianceFindingDTO.setStatus(complianceFinding.getStatus());
		
		return complianceFindingDTO;
	}

	public ResultDTO updateComplianceFinding(ComplianceFindingDTO complianceFindingDTO, RequestDTO requestDTO) {
		
		ComplianceFinding complianceFinding = complianceFindingDao.getById(complianceFindingDTO.getComplianceFindingId());
		
		complianceFinding.setComplianceFindingId(ControllerUtils.setValue(complianceFinding.getComplianceFindingId(), complianceFindingDTO.getComplianceFindingId()));
		
		complianceFinding.setComplianceAssessmentId(ControllerUtils.setValue(complianceFinding.getComplianceAssessmentId(), complianceFindingDTO.getComplianceAssessmentId()));
		
		complianceFinding.setComplianceControlId(ControllerUtils.setValue(complianceFinding.getComplianceControlId(), complianceFindingDTO.getComplianceControlId()));
		
		complianceFinding.setSeverity(ControllerUtils.setValue(complianceFinding.getSeverity(), complianceFindingDTO.getSeverity()));
		
		complianceFinding.setDescription(ControllerUtils.setValue(complianceFinding.getDescription(), complianceFindingDTO.getDescription()));
		
		complianceFinding.setStatus(ControllerUtils.setValue(complianceFinding.getStatus(), complianceFindingDTO.getStatus()));

        complianceFinding = complianceFindingDao.save(complianceFinding);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ComplianceFindingDTO getComplianceFindingDTOById(Integer complianceFindingId) {
	
		ComplianceFinding complianceFinding = complianceFindingDao.getById(complianceFindingId);
		
		ComplianceFindingConvertCriteriaDTO convertCriteria = new ComplianceFindingConvertCriteriaDTO();
		return(this.convertComplianceFindingToComplianceFindingDTO(complianceFinding,convertCriteria));
	}

}

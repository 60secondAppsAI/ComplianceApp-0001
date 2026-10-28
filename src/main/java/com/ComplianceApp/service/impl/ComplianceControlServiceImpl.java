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
import com.ComplianceApp.dao.ComplianceControlDAO;
import com.ComplianceApp.domain.ComplianceControl;
import com.ComplianceApp.dto.ComplianceControlDTO;
import com.ComplianceApp.dto.ComplianceControlSearchDTO;
import com.ComplianceApp.dto.ComplianceControlPageDTO;
import com.ComplianceApp.dto.ComplianceControlConvertCriteriaDTO;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import com.ComplianceApp.service.ComplianceControlService;
import com.ComplianceApp.util.ControllerUtils;


@Service
public class ComplianceControlServiceImpl extends GenericServiceImpl<ComplianceControl, Integer> implements ComplianceControlService {

    private final static Logger logger = LoggerFactory.getLogger(ComplianceControlServiceImpl.class);

	@Autowired
	ComplianceControlDAO complianceControlDao;

	

	@Override
	public GenericDAO<ComplianceControl, Integer> getDAO() {
		return (GenericDAO<ComplianceControl, Integer>) complianceControlDao;
	}
	
	public List<ComplianceControl> findAll () {
		List<ComplianceControl> complianceControls = complianceControlDao.findAll();
		
		return complianceControls;	
		
	}

	public ResultDTO addComplianceControl(ComplianceControlDTO complianceControlDTO, RequestDTO requestDTO) {

		ComplianceControl complianceControl = new ComplianceControl();

		complianceControl.setComplianceControlId(complianceControlDTO.getComplianceControlId());

		complianceControl.setComplianceFrameworkId(complianceControlDTO.getComplianceFrameworkId());

		complianceControl.setControlCode(complianceControlDTO.getControlCode());

		complianceControl.setTitle(complianceControlDTO.getTitle());

		complianceControl.setDescription(complianceControlDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		complianceControl = complianceControlDao.save(complianceControl);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ComplianceControl> getAllComplianceControls(Pageable pageable) {
		return complianceControlDao.findAll(pageable);
	}

	public Page<ComplianceControl> getAllComplianceControls(Specification<ComplianceControl> spec, Pageable pageable) {
		return complianceControlDao.findAll(spec, pageable);
	}

	public ResponseEntity<ComplianceControlPageDTO> getComplianceControls(ComplianceControlSearchDTO complianceControlSearchDTO) {
	
			Integer complianceControlId = complianceControlSearchDTO.getComplianceControlId(); 
 			Integer complianceFrameworkId = complianceControlSearchDTO.getComplianceFrameworkId(); 
 			String controlCode = complianceControlSearchDTO.getControlCode(); 
 			String title = complianceControlSearchDTO.getTitle(); 
 			String description = complianceControlSearchDTO.getDescription(); 
 			String sortBy = complianceControlSearchDTO.getSortBy();
			String sortOrder = complianceControlSearchDTO.getSortOrder();
			String searchQuery = complianceControlSearchDTO.getSearchQuery();
			Integer page = complianceControlSearchDTO.getPage();
			Integer size = complianceControlSearchDTO.getSize();

	        Specification<ComplianceControl> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, complianceControlId, "complianceControlId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, complianceFrameworkId, "complianceFrameworkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, controlCode, "controlCode"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("controlCode")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ComplianceControl> complianceControls = this.getAllComplianceControls(spec, pageable);
		
		//System.out.println(String.valueOf(complianceControls.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(complianceControls.getTotalPages()));
		
		List<ComplianceControl> complianceControlsList = complianceControls.getContent();
		
		ComplianceControlConvertCriteriaDTO convertCriteria = new ComplianceControlConvertCriteriaDTO();
		List<ComplianceControlDTO> complianceControlDTOs = this.convertComplianceControlsToComplianceControlDTOs(complianceControlsList,convertCriteria);
		
		ComplianceControlPageDTO complianceControlPageDTO = new ComplianceControlPageDTO();
		complianceControlPageDTO.setComplianceControls(complianceControlDTOs);
		complianceControlPageDTO.setTotalElements(complianceControls.getTotalElements());
		return ResponseEntity.ok(complianceControlPageDTO);
	}

	public List<ComplianceControlDTO> convertComplianceControlsToComplianceControlDTOs(List<ComplianceControl> complianceControls, ComplianceControlConvertCriteriaDTO convertCriteria) {
		
		List<ComplianceControlDTO> complianceControlDTOs = new ArrayList<ComplianceControlDTO>();
		
		for (ComplianceControl complianceControl : complianceControls) {
			complianceControlDTOs.add(convertComplianceControlToComplianceControlDTO(complianceControl,convertCriteria));
		}
		
		return complianceControlDTOs;

	}
	
	public ComplianceControlDTO convertComplianceControlToComplianceControlDTO(ComplianceControl complianceControl, ComplianceControlConvertCriteriaDTO convertCriteria) {
		
		ComplianceControlDTO complianceControlDTO = new ComplianceControlDTO();

		complianceControlDTO.setComplianceControlId(complianceControl.getComplianceControlId());

		complianceControlDTO.setComplianceFrameworkId(complianceControl.getComplianceFrameworkId());

		complianceControlDTO.setControlCode(complianceControl.getControlCode());

		complianceControlDTO.setTitle(complianceControl.getTitle());

		complianceControlDTO.setDescription(complianceControl.getDescription());
		
		return complianceControlDTO;
	}

	public ResultDTO updateComplianceControl(ComplianceControlDTO complianceControlDTO, RequestDTO requestDTO) {
		
		ComplianceControl complianceControl = complianceControlDao.getById(complianceControlDTO.getComplianceControlId());
		
		complianceControl.setComplianceControlId(ControllerUtils.setValue(complianceControl.getComplianceControlId(), complianceControlDTO.getComplianceControlId()));
		
		complianceControl.setComplianceFrameworkId(ControllerUtils.setValue(complianceControl.getComplianceFrameworkId(), complianceControlDTO.getComplianceFrameworkId()));
		
		complianceControl.setControlCode(ControllerUtils.setValue(complianceControl.getControlCode(), complianceControlDTO.getControlCode()));
		
		complianceControl.setTitle(ControllerUtils.setValue(complianceControl.getTitle(), complianceControlDTO.getTitle()));
		
		complianceControl.setDescription(ControllerUtils.setValue(complianceControl.getDescription(), complianceControlDTO.getDescription()));

        complianceControl = complianceControlDao.save(complianceControl);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ComplianceControlDTO getComplianceControlDTOById(Integer complianceControlId) {
	
		ComplianceControl complianceControl = complianceControlDao.getById(complianceControlId);
		
		ComplianceControlConvertCriteriaDTO convertCriteria = new ComplianceControlConvertCriteriaDTO();
		return(this.convertComplianceControlToComplianceControlDTO(complianceControl,convertCriteria));
	}

}

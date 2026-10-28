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
import com.ComplianceApp.dao.ComplianceFrameworkDAO;
import com.ComplianceApp.domain.ComplianceFramework;
import com.ComplianceApp.dto.ComplianceFrameworkDTO;
import com.ComplianceApp.dto.ComplianceFrameworkSearchDTO;
import com.ComplianceApp.dto.ComplianceFrameworkPageDTO;
import com.ComplianceApp.dto.ComplianceFrameworkConvertCriteriaDTO;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import com.ComplianceApp.service.ComplianceFrameworkService;
import com.ComplianceApp.util.ControllerUtils;


@Service
public class ComplianceFrameworkServiceImpl extends GenericServiceImpl<ComplianceFramework, Integer> implements ComplianceFrameworkService {

    private final static Logger logger = LoggerFactory.getLogger(ComplianceFrameworkServiceImpl.class);

	@Autowired
	ComplianceFrameworkDAO complianceFrameworkDao;

	

	@Override
	public GenericDAO<ComplianceFramework, Integer> getDAO() {
		return (GenericDAO<ComplianceFramework, Integer>) complianceFrameworkDao;
	}
	
	public List<ComplianceFramework> findAll () {
		List<ComplianceFramework> complianceFrameworks = complianceFrameworkDao.findAll();
		
		return complianceFrameworks;	
		
	}

	public ResultDTO addComplianceFramework(ComplianceFrameworkDTO complianceFrameworkDTO, RequestDTO requestDTO) {

		ComplianceFramework complianceFramework = new ComplianceFramework();

		complianceFramework.setComplianceFrameworkId(complianceFrameworkDTO.getComplianceFrameworkId());

		complianceFramework.setName(complianceFrameworkDTO.getName());

		complianceFramework.setVersion(complianceFrameworkDTO.getVersion());

		complianceFramework.setDescription(complianceFrameworkDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		complianceFramework = complianceFrameworkDao.save(complianceFramework);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ComplianceFramework> getAllComplianceFrameworks(Pageable pageable) {
		return complianceFrameworkDao.findAll(pageable);
	}

	public Page<ComplianceFramework> getAllComplianceFrameworks(Specification<ComplianceFramework> spec, Pageable pageable) {
		return complianceFrameworkDao.findAll(spec, pageable);
	}

	public ResponseEntity<ComplianceFrameworkPageDTO> getComplianceFrameworks(ComplianceFrameworkSearchDTO complianceFrameworkSearchDTO) {
	
			Integer complianceFrameworkId = complianceFrameworkSearchDTO.getComplianceFrameworkId(); 
 			String name = complianceFrameworkSearchDTO.getName(); 
 			String version = complianceFrameworkSearchDTO.getVersion(); 
 			String description = complianceFrameworkSearchDTO.getDescription(); 
 			String sortBy = complianceFrameworkSearchDTO.getSortBy();
			String sortOrder = complianceFrameworkSearchDTO.getSortOrder();
			String searchQuery = complianceFrameworkSearchDTO.getSearchQuery();
			Integer page = complianceFrameworkSearchDTO.getPage();
			Integer size = complianceFrameworkSearchDTO.getSize();

	        Specification<ComplianceFramework> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, complianceFrameworkId, "complianceFrameworkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, version, "version"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("version")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ComplianceFramework> complianceFrameworks = this.getAllComplianceFrameworks(spec, pageable);
		
		//System.out.println(String.valueOf(complianceFrameworks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(complianceFrameworks.getTotalPages()));
		
		List<ComplianceFramework> complianceFrameworksList = complianceFrameworks.getContent();
		
		ComplianceFrameworkConvertCriteriaDTO convertCriteria = new ComplianceFrameworkConvertCriteriaDTO();
		List<ComplianceFrameworkDTO> complianceFrameworkDTOs = this.convertComplianceFrameworksToComplianceFrameworkDTOs(complianceFrameworksList,convertCriteria);
		
		ComplianceFrameworkPageDTO complianceFrameworkPageDTO = new ComplianceFrameworkPageDTO();
		complianceFrameworkPageDTO.setComplianceFrameworks(complianceFrameworkDTOs);
		complianceFrameworkPageDTO.setTotalElements(complianceFrameworks.getTotalElements());
		return ResponseEntity.ok(complianceFrameworkPageDTO);
	}

	public List<ComplianceFrameworkDTO> convertComplianceFrameworksToComplianceFrameworkDTOs(List<ComplianceFramework> complianceFrameworks, ComplianceFrameworkConvertCriteriaDTO convertCriteria) {
		
		List<ComplianceFrameworkDTO> complianceFrameworkDTOs = new ArrayList<ComplianceFrameworkDTO>();
		
		for (ComplianceFramework complianceFramework : complianceFrameworks) {
			complianceFrameworkDTOs.add(convertComplianceFrameworkToComplianceFrameworkDTO(complianceFramework,convertCriteria));
		}
		
		return complianceFrameworkDTOs;

	}
	
	public ComplianceFrameworkDTO convertComplianceFrameworkToComplianceFrameworkDTO(ComplianceFramework complianceFramework, ComplianceFrameworkConvertCriteriaDTO convertCriteria) {
		
		ComplianceFrameworkDTO complianceFrameworkDTO = new ComplianceFrameworkDTO();

		complianceFrameworkDTO.setComplianceFrameworkId(complianceFramework.getComplianceFrameworkId());

		complianceFrameworkDTO.setName(complianceFramework.getName());

		complianceFrameworkDTO.setVersion(complianceFramework.getVersion());

		complianceFrameworkDTO.setDescription(complianceFramework.getDescription());
		
		return complianceFrameworkDTO;
	}

	public ResultDTO updateComplianceFramework(ComplianceFrameworkDTO complianceFrameworkDTO, RequestDTO requestDTO) {
		
		ComplianceFramework complianceFramework = complianceFrameworkDao.getById(complianceFrameworkDTO.getComplianceFrameworkId());
		
		complianceFramework.setComplianceFrameworkId(ControllerUtils.setValue(complianceFramework.getComplianceFrameworkId(), complianceFrameworkDTO.getComplianceFrameworkId()));
		
		complianceFramework.setName(ControllerUtils.setValue(complianceFramework.getName(), complianceFrameworkDTO.getName()));
		
		complianceFramework.setVersion(ControllerUtils.setValue(complianceFramework.getVersion(), complianceFrameworkDTO.getVersion()));
		
		complianceFramework.setDescription(ControllerUtils.setValue(complianceFramework.getDescription(), complianceFrameworkDTO.getDescription()));

        complianceFramework = complianceFrameworkDao.save(complianceFramework);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ComplianceFrameworkDTO getComplianceFrameworkDTOById(Integer complianceFrameworkId) {
	
		ComplianceFramework complianceFramework = complianceFrameworkDao.getById(complianceFrameworkId);
		
		ComplianceFrameworkConvertCriteriaDTO convertCriteria = new ComplianceFrameworkConvertCriteriaDTO();
		return(this.convertComplianceFrameworkToComplianceFrameworkDTO(complianceFramework,convertCriteria));
	}

}

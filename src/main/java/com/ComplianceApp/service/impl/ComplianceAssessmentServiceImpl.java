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
import com.ComplianceApp.dao.ComplianceAssessmentDAO;
import com.ComplianceApp.domain.ComplianceAssessment;
import com.ComplianceApp.dto.ComplianceAssessmentDTO;
import com.ComplianceApp.dto.ComplianceAssessmentSearchDTO;
import com.ComplianceApp.dto.ComplianceAssessmentPageDTO;
import com.ComplianceApp.dto.ComplianceAssessmentConvertCriteriaDTO;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;
import com.ComplianceApp.service.ComplianceAssessmentService;
import com.ComplianceApp.util.ControllerUtils;


@Service
public class ComplianceAssessmentServiceImpl extends GenericServiceImpl<ComplianceAssessment, Integer> implements ComplianceAssessmentService {

    private final static Logger logger = LoggerFactory.getLogger(ComplianceAssessmentServiceImpl.class);

	@Autowired
	ComplianceAssessmentDAO complianceAssessmentDao;

	

	@Override
	public GenericDAO<ComplianceAssessment, Integer> getDAO() {
		return (GenericDAO<ComplianceAssessment, Integer>) complianceAssessmentDao;
	}
	
	public List<ComplianceAssessment> findAll () {
		List<ComplianceAssessment> complianceAssessments = complianceAssessmentDao.findAll();
		
		return complianceAssessments;	
		
	}

	public ResultDTO addComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO, RequestDTO requestDTO) {

		ComplianceAssessment complianceAssessment = new ComplianceAssessment();

		complianceAssessment.setComplianceAssessmentId(complianceAssessmentDTO.getComplianceAssessmentId());

		complianceAssessment.setComplianceFrameworkId(complianceAssessmentDTO.getComplianceFrameworkId());

		complianceAssessment.setAssessmentDate(complianceAssessmentDTO.getAssessmentDate());

		complianceAssessment.setAssessor(complianceAssessmentDTO.getAssessor());

		complianceAssessment.setStatus(complianceAssessmentDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		complianceAssessment = complianceAssessmentDao.save(complianceAssessment);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ComplianceAssessment> getAllComplianceAssessments(Pageable pageable) {
		return complianceAssessmentDao.findAll(pageable);
	}

	public Page<ComplianceAssessment> getAllComplianceAssessments(Specification<ComplianceAssessment> spec, Pageable pageable) {
		return complianceAssessmentDao.findAll(spec, pageable);
	}

	public ResponseEntity<ComplianceAssessmentPageDTO> getComplianceAssessments(ComplianceAssessmentSearchDTO complianceAssessmentSearchDTO) {
	
			Integer complianceAssessmentId = complianceAssessmentSearchDTO.getComplianceAssessmentId(); 
 			Integer complianceFrameworkId = complianceAssessmentSearchDTO.getComplianceFrameworkId(); 
 			Date assessmentDate = complianceAssessmentSearchDTO.getAssessmentDate();
 			String assessor = complianceAssessmentSearchDTO.getAssessor(); 
 			String status = complianceAssessmentSearchDTO.getStatus(); 
 			String sortBy = complianceAssessmentSearchDTO.getSortBy();
			String sortOrder = complianceAssessmentSearchDTO.getSortOrder();
			String searchQuery = complianceAssessmentSearchDTO.getSearchQuery();
			Integer page = complianceAssessmentSearchDTO.getPage();
			Integer size = complianceAssessmentSearchDTO.getSize();

	        Specification<ComplianceAssessment> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, complianceAssessmentId, "complianceAssessmentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, complianceFrameworkId, "complianceFrameworkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, assessmentDate, "assessmentDate");
			
			spec = ControllerUtils.andIfNecessary(spec, assessor, "assessor"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("assessor")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ComplianceAssessment> complianceAssessments = this.getAllComplianceAssessments(spec, pageable);
		
		//System.out.println(String.valueOf(complianceAssessments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(complianceAssessments.getTotalPages()));
		
		List<ComplianceAssessment> complianceAssessmentsList = complianceAssessments.getContent();
		
		ComplianceAssessmentConvertCriteriaDTO convertCriteria = new ComplianceAssessmentConvertCriteriaDTO();
		List<ComplianceAssessmentDTO> complianceAssessmentDTOs = this.convertComplianceAssessmentsToComplianceAssessmentDTOs(complianceAssessmentsList,convertCriteria);
		
		ComplianceAssessmentPageDTO complianceAssessmentPageDTO = new ComplianceAssessmentPageDTO();
		complianceAssessmentPageDTO.setComplianceAssessments(complianceAssessmentDTOs);
		complianceAssessmentPageDTO.setTotalElements(complianceAssessments.getTotalElements());
		return ResponseEntity.ok(complianceAssessmentPageDTO);
	}

	public List<ComplianceAssessmentDTO> convertComplianceAssessmentsToComplianceAssessmentDTOs(List<ComplianceAssessment> complianceAssessments, ComplianceAssessmentConvertCriteriaDTO convertCriteria) {
		
		List<ComplianceAssessmentDTO> complianceAssessmentDTOs = new ArrayList<ComplianceAssessmentDTO>();
		
		for (ComplianceAssessment complianceAssessment : complianceAssessments) {
			complianceAssessmentDTOs.add(convertComplianceAssessmentToComplianceAssessmentDTO(complianceAssessment,convertCriteria));
		}
		
		return complianceAssessmentDTOs;

	}
	
	public ComplianceAssessmentDTO convertComplianceAssessmentToComplianceAssessmentDTO(ComplianceAssessment complianceAssessment, ComplianceAssessmentConvertCriteriaDTO convertCriteria) {
		
		ComplianceAssessmentDTO complianceAssessmentDTO = new ComplianceAssessmentDTO();

		complianceAssessmentDTO.setComplianceAssessmentId(complianceAssessment.getComplianceAssessmentId());

		complianceAssessmentDTO.setComplianceFrameworkId(complianceAssessment.getComplianceFrameworkId());

		complianceAssessmentDTO.setAssessmentDate(complianceAssessment.getAssessmentDate());

		complianceAssessmentDTO.setAssessor(complianceAssessment.getAssessor());

		complianceAssessmentDTO.setStatus(complianceAssessment.getStatus());
		
		return complianceAssessmentDTO;
	}

	public ResultDTO updateComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO, RequestDTO requestDTO) {
		
		ComplianceAssessment complianceAssessment = complianceAssessmentDao.getById(complianceAssessmentDTO.getComplianceAssessmentId());
		
		complianceAssessment.setComplianceAssessmentId(ControllerUtils.setValue(complianceAssessment.getComplianceAssessmentId(), complianceAssessmentDTO.getComplianceAssessmentId()));
		
		complianceAssessment.setComplianceFrameworkId(ControllerUtils.setValue(complianceAssessment.getComplianceFrameworkId(), complianceAssessmentDTO.getComplianceFrameworkId()));
		
		complianceAssessment.setAssessmentDate(ControllerUtils.setValue(complianceAssessment.getAssessmentDate(), complianceAssessmentDTO.getAssessmentDate()));
		
		complianceAssessment.setAssessor(ControllerUtils.setValue(complianceAssessment.getAssessor(), complianceAssessmentDTO.getAssessor()));
		
		complianceAssessment.setStatus(ControllerUtils.setValue(complianceAssessment.getStatus(), complianceAssessmentDTO.getStatus()));

        complianceAssessment = complianceAssessmentDao.save(complianceAssessment);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ComplianceAssessmentDTO getComplianceAssessmentDTOById(Integer complianceAssessmentId) {
	
		ComplianceAssessment complianceAssessment = complianceAssessmentDao.getById(complianceAssessmentId);
		
		ComplianceAssessmentConvertCriteriaDTO convertCriteria = new ComplianceAssessmentConvertCriteriaDTO();
		return(this.convertComplianceAssessmentToComplianceAssessmentDTO(complianceAssessment,convertCriteria));
	}

}

package com.ComplianceApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.ComplianceApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.ComplianceApp.domain.ComplianceAssessment;
import com.ComplianceApp.dto.ComplianceAssessmentDTO;
import com.ComplianceApp.dto.ComplianceAssessmentSearchDTO;
import com.ComplianceApp.dto.ComplianceAssessmentPageDTO;
import com.ComplianceApp.service.ComplianceAssessmentService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/complianceAssessment")
@RestController
public class ComplianceAssessmentController {

	private final static Logger logger = LoggerFactory.getLogger(ComplianceAssessmentController.class);

	@Autowired
	ComplianceAssessmentService complianceAssessmentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ComplianceAssessment> getAll() {

		List<ComplianceAssessment> complianceAssessments = complianceAssessmentService.findAll();
		
		return complianceAssessments;	
	}

	@GetMapping(value = "/{complianceAssessmentId}")
	@ResponseBody
	public ComplianceAssessmentDTO getComplianceAssessment(@PathVariable Integer complianceAssessmentId) {
		
		return (complianceAssessmentService.getComplianceAssessmentDTOById(complianceAssessmentId));
	}

 	@RequestMapping(value = "/addComplianceAssessment", method = RequestMethod.POST)
	public ResponseEntity<?> addComplianceAssessment(@RequestBody ComplianceAssessmentDTO complianceAssessmentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceAssessmentService.addComplianceAssessment(complianceAssessmentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/complianceAssessments")
	public ResponseEntity<ComplianceAssessmentPageDTO> getComplianceAssessments(ComplianceAssessmentSearchDTO complianceAssessmentSearchDTO) {
 
		return complianceAssessmentService.getComplianceAssessments(complianceAssessmentSearchDTO);
	}	

	@RequestMapping(value = "/updateComplianceAssessment", method = RequestMethod.POST)
	public ResponseEntity<?> updateComplianceAssessment(@RequestBody ComplianceAssessmentDTO complianceAssessmentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceAssessmentService.updateComplianceAssessment(complianceAssessmentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

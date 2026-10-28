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

import com.ComplianceApp.domain.ComplianceFinding;
import com.ComplianceApp.dto.ComplianceFindingDTO;
import com.ComplianceApp.dto.ComplianceFindingSearchDTO;
import com.ComplianceApp.dto.ComplianceFindingPageDTO;
import com.ComplianceApp.service.ComplianceFindingService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/complianceFinding")
@RestController
public class ComplianceFindingController {

	private final static Logger logger = LoggerFactory.getLogger(ComplianceFindingController.class);

	@Autowired
	ComplianceFindingService complianceFindingService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ComplianceFinding> getAll() {

		List<ComplianceFinding> complianceFindings = complianceFindingService.findAll();
		
		return complianceFindings;	
	}

	@GetMapping(value = "/{complianceFindingId}")
	@ResponseBody
	public ComplianceFindingDTO getComplianceFinding(@PathVariable Integer complianceFindingId) {
		
		return (complianceFindingService.getComplianceFindingDTOById(complianceFindingId));
	}

 	@RequestMapping(value = "/addComplianceFinding", method = RequestMethod.POST)
	public ResponseEntity<?> addComplianceFinding(@RequestBody ComplianceFindingDTO complianceFindingDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceFindingService.addComplianceFinding(complianceFindingDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/complianceFindings")
	public ResponseEntity<ComplianceFindingPageDTO> getComplianceFindings(ComplianceFindingSearchDTO complianceFindingSearchDTO) {
 
		return complianceFindingService.getComplianceFindings(complianceFindingSearchDTO);
	}	

	@RequestMapping(value = "/updateComplianceFinding", method = RequestMethod.POST)
	public ResponseEntity<?> updateComplianceFinding(@RequestBody ComplianceFindingDTO complianceFindingDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceFindingService.updateComplianceFinding(complianceFindingDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

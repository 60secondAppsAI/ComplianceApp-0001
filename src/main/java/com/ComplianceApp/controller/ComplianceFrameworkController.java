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

import com.ComplianceApp.domain.ComplianceFramework;
import com.ComplianceApp.dto.ComplianceFrameworkDTO;
import com.ComplianceApp.dto.ComplianceFrameworkSearchDTO;
import com.ComplianceApp.dto.ComplianceFrameworkPageDTO;
import com.ComplianceApp.service.ComplianceFrameworkService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/complianceFramework")
@RestController
public class ComplianceFrameworkController {

	private final static Logger logger = LoggerFactory.getLogger(ComplianceFrameworkController.class);

	@Autowired
	ComplianceFrameworkService complianceFrameworkService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ComplianceFramework> getAll() {

		List<ComplianceFramework> complianceFrameworks = complianceFrameworkService.findAll();
		
		return complianceFrameworks;	
	}

	@GetMapping(value = "/{complianceFrameworkId}")
	@ResponseBody
	public ComplianceFrameworkDTO getComplianceFramework(@PathVariable Integer complianceFrameworkId) {
		
		return (complianceFrameworkService.getComplianceFrameworkDTOById(complianceFrameworkId));
	}

 	@RequestMapping(value = "/addComplianceFramework", method = RequestMethod.POST)
	public ResponseEntity<?> addComplianceFramework(@RequestBody ComplianceFrameworkDTO complianceFrameworkDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceFrameworkService.addComplianceFramework(complianceFrameworkDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/complianceFrameworks")
	public ResponseEntity<ComplianceFrameworkPageDTO> getComplianceFrameworks(ComplianceFrameworkSearchDTO complianceFrameworkSearchDTO) {
 
		return complianceFrameworkService.getComplianceFrameworks(complianceFrameworkSearchDTO);
	}	

	@RequestMapping(value = "/updateComplianceFramework", method = RequestMethod.POST)
	public ResponseEntity<?> updateComplianceFramework(@RequestBody ComplianceFrameworkDTO complianceFrameworkDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceFrameworkService.updateComplianceFramework(complianceFrameworkDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

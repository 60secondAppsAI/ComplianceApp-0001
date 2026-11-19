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

import com.ComplianceApp.domain.ComplianceControl;
import com.ComplianceApp.dto.ComplianceControlDTO;
import com.ComplianceApp.dto.ComplianceControlSearchDTO;
import com.ComplianceApp.dto.ComplianceControlPageDTO;
import com.ComplianceApp.service.ComplianceControlService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/complianceControl")
@RestController
public class ComplianceControlController {

	private final static Logger logger = LoggerFactory.getLogger(ComplianceControlController.class);

	@Autowired
	ComplianceControlService complianceControlService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ComplianceControl> getAll() {

		List<ComplianceControl> complianceControls = complianceControlService.findAll();
		
		return complianceControls;	
	}

	@GetMapping(value = "/{complianceControlId}")
	@ResponseBody
	public ComplianceControlDTO getComplianceControl(@PathVariable Integer complianceControlId) {
		
		return (complianceControlService.getComplianceControlDTOById(complianceControlId));
	}

 	@RequestMapping(value = "/addComplianceControl", method = RequestMethod.POST)
	public ResponseEntity<?> addComplianceControl(@RequestBody ComplianceControlDTO complianceControlDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceControlService.addComplianceControl(complianceControlDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/complianceControls")
	public ResponseEntity<ComplianceControlPageDTO> getComplianceControls(ComplianceControlSearchDTO complianceControlSearchDTO) {
 
		return complianceControlService.getComplianceControls(complianceControlSearchDTO);
	}	

	@RequestMapping(value = "/updateComplianceControl", method = RequestMethod.POST)
	public ResponseEntity<?> updateComplianceControl(@RequestBody ComplianceControlDTO complianceControlDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complianceControlService.updateComplianceControl(complianceControlDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

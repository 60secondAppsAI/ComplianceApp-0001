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

import com.ComplianceApp.domain.RemediationAction;
import com.ComplianceApp.dto.RemediationActionDTO;
import com.ComplianceApp.dto.RemediationActionSearchDTO;
import com.ComplianceApp.dto.RemediationActionPageDTO;
import com.ComplianceApp.service.RemediationActionService;
import com.ComplianceApp.dto.common.RequestDTO;
import com.ComplianceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/remediationAction")
@RestController
public class RemediationActionController {

	private final static Logger logger = LoggerFactory.getLogger(RemediationActionController.class);

	@Autowired
	RemediationActionService remediationActionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RemediationAction> getAll() {

		List<RemediationAction> remediationActions = remediationActionService.findAll();
		
		return remediationActions;	
	}

	@GetMapping(value = "/{remediationActionId}")
	@ResponseBody
	public RemediationActionDTO getRemediationAction(@PathVariable Integer remediationActionId) {
		
		return (remediationActionService.getRemediationActionDTOById(remediationActionId));
	}

 	@RequestMapping(value = "/addRemediationAction", method = RequestMethod.POST)
	public ResponseEntity<?> addRemediationAction(@RequestBody RemediationActionDTO remediationActionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = remediationActionService.addRemediationAction(remediationActionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/remediationActions")
	public ResponseEntity<RemediationActionPageDTO> getRemediationActions(RemediationActionSearchDTO remediationActionSearchDTO) {
 
		return remediationActionService.getRemediationActions(remediationActionSearchDTO);
	}	

	@RequestMapping(value = "/updateRemediationAction", method = RequestMethod.POST)
	public ResponseEntity<?> updateRemediationAction(@RequestBody RemediationActionDTO remediationActionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = remediationActionService.updateRemediationAction(remediationActionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

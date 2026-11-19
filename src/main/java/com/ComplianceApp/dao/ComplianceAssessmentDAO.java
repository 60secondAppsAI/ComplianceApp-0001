package com.ComplianceApp.dao;

import java.util.List;
import java.util.Date;

import com.ComplianceApp.dao.GenericDAO;
import com.ComplianceApp.domain.ComplianceAssessment;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ComplianceAssessmentDAO extends GenericDAO<ComplianceAssessment, Integer> {
  
	List<ComplianceAssessment> findAll();
	


}


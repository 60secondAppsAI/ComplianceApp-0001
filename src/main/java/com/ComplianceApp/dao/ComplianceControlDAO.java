package com.ComplianceApp.dao;

import java.util.List;
import java.util.Date;

import com.ComplianceApp.dao.GenericDAO;
import com.ComplianceApp.domain.ComplianceControl;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ComplianceControlDAO extends GenericDAO<ComplianceControl, Integer> {
  
	List<ComplianceControl> findAll();
	


}


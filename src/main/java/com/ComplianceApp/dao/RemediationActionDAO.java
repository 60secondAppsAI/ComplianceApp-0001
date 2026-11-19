package com.ComplianceApp.dao;

import java.util.List;
import java.util.Date;

import com.ComplianceApp.dao.GenericDAO;
import com.ComplianceApp.domain.RemediationAction;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RemediationActionDAO extends GenericDAO<RemediationAction, Integer> {
  
	List<RemediationAction> findAll();
	


}


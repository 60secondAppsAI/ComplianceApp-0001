package com.ComplianceApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="remediation_actions")
@Getter @Setter @NoArgsConstructor
public class RemediationAction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="remediation_action_id")
	private Integer remediationActionId;
    
  	@Column(name="compliance_finding_id")
	private Integer complianceFindingId;
    
  	@Column(name="description")
	private String description;
    
  	@Column(name="owner")
	private String owner;
    
  	@Column(name="due_date")
	private Date dueDate;
    
  	@Column(name="status")
	private String status;
    

}

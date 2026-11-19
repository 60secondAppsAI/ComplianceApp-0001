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
@Table(name="compliance_frameworks")
@Getter @Setter @NoArgsConstructor
public class ComplianceFramework {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="compliance_framework_id")
	private Integer complianceFrameworkId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="version")
	private String version;
    
  	@Column(name="description")
	private String description;
    

}

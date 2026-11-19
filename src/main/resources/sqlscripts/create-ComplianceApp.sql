CREATE DATABASE IF NOT EXISTS ComplianceApp;

CREATE TABLE ComplianceApp.compliance_frameworks (
    `compliance_framework_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `version` VARCHAR(255),
    `description` VARCHAR(255),
PRIMARY KEY (compliance_framework_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ComplianceApp.compliance_controls (
    `compliance_control_id` INT AUTO_INCREMENT,
    `compliance_framework_id` INT,
    `control_code` VARCHAR(255),
    `title` VARCHAR(255),
    `description` VARCHAR(255),
PRIMARY KEY (compliance_control_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ComplianceApp.compliance_assessments (
    `compliance_assessment_id` INT AUTO_INCREMENT,
    `compliance_framework_id` INT,
    `assessment_date` DATETIME,
    `assessor` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (compliance_assessment_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ComplianceApp.compliance_findings (
    `compliance_finding_id` INT AUTO_INCREMENT,
    `compliance_assessment_id` INT,
    `compliance_control_id` INT,
    `severity` VARCHAR(255),
    `description` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (compliance_finding_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ComplianceApp.remediation_actions (
    `remediation_action_id` INT AUTO_INCREMENT,
    `compliance_finding_id` INT,
    `description` VARCHAR(255),
    `owner` VARCHAR(255),
    `due_date` DATETIME,
    `status` VARCHAR(255),
PRIMARY KEY (remediation_action_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;

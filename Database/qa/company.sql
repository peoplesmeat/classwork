-- ============================================================================
--
--  file:
--    ~eai/oracle/company/company.sql
--
--  This file contains modified DDL and data statements for the Company 
--  database example shown in Fig. 5.6, _Fundamentals of Database Systems_ 
--  (5th ed.), Elmasri and Navathe, 2006.
--
-- ============================================================================


CREATE TABLE EMPLOYEE (
	FNAME		VARCHAR(15)		NOT NULL,
	MINIT		CHAR,
	LNAME		VARCHAR(15)		NOT NULL,
	SSN			CHAR(9)			NOT NULL,
	BDATE		DATE,
	ADDRESS		VARCHAR(30),
	SEX			CHAR,
	SALARY		DECIMAL(10,2),
	SUPERSSN	CHAR(9),
	DNO			INT				NOT NULL,
	PRIMARY KEY (SSN)
);


CREATE TABLE DEPARTMENT (
	DNAME			VARCHAR(15)	NOT NULL,
	DNUMBER			INT			NOT NULL,
	MGRSSN			CHAR(9)		NOT NULL,
	MGRSTARTDATE	DATE,
	PRIMARY KEY (DNUMBER) 
);


CREATE TABLE DEPT_LOCATIONS (
	DNUMBER		INT			NOT NULL,
	DLOCATION	VARCHAR(15)	NOT NULL,
	PRIMARY KEY (DNUMBER,DLOCATION)
);


CREATE TABLE PROJECT (
	PNAME		VARCHAR(15)	NOT NULL,
	PNUMBER		INT			NOT NULL,
	PLOCATION	VARCHAR(15),
	DNUM		INT			NOT NULL,
	PRIMARY KEY (PNUMBER)
);


CREATE TABLE WORKS_ON (
	ESSN	CHAR(9)		NOT NULL,
	PNO		INT			NOT NULL,
	HOURS	DECIMAL(3,1),
	PRIMARY KEY (ESSN,PNO)
);


CREATE TABLE DEPENDENT (
	ESSN			CHAR(9)		NOT NULL,
	DEPENDENT_NAME	VARCHAR(15)	NOT NULL,
	SEX				CHAR,
	BDATE			DATE,
	RELATIONSHIP	VARCHAR(8),
	PRIMARY KEY (ESSN,DEPENDENT_NAME)
);



INSERT into EMPLOYEE Values ('John', 'B', 'Smith', '123456789', '09-JAN-65',
                             '731 Fondren, Houston, TX', 'M', 50000, 
                             '333445555', 5);
INSERT into EMPLOYEE Values ('Franklin', 'T', 'Wong', '333445555', '08-DEC-55',
                             '638 Voss, Houston, TX', 'M', 60000, 
                             '888665555', 5);
INSERT into EMPLOYEE Values ('Alicia', 'J', 'Zelaya', '999887777', '19-JUL-68',
                             '3321 Castle, Spring, TX', 'F', 45000, 
                             '987654321', 4);
INSERT into EMPLOYEE Values ('John', 'S', 'Jones', '123123123', '13-JUN-57',
                             '57 Knollwood, Houston, TX', 'M', 65000, 
                             333445555, 5);
INSERT into EMPLOYEE Values ('Jennifer', 'S', 'Wallace', '987654321', '20-JUN-41',
                             '291 Berry, Bellaire, TX', 'F', 63000, 
                             '888665555', 4);
INSERT into EMPLOYEE Values ('Ramesh', 'K', 'Narayan', '666884444', '15-SEP-62',
                             '975 Fire Oak, Humble, TX', 'M', 58000, 
                             '333445555', 5);
INSERT into EMPLOYEE Values ('Joyce', 'A', 'English', '453453453', '31-JUL-72',
                             '5631 Rice, Houston, TX', 'F', 45000, 
                             '333445555', 5);
INSERT into EMPLOYEE Values ('Jane', 'C', 'Stone', '777220123', '12-DEC-68',
                             '5962 Tumbleweed, Houston, TX', 'F', 46000, 
                             '333445555', 5);
INSERT into EMPLOYEE Values ('Ahmad', 'V', 'Jabbar', '987987987', '29-MAR-69',
                             '980 Dallas, Houston, TX', 'M', 45000, 
                             '987654321', 4);
INSERT into EMPLOYEE Values ('James', 'E', 'Borg', '888665555', '10-NOV-37',
                             '450 Stone, Houston, TX', 'M', 75000, 
                             null, 1);
INSERT into EMPLOYEE Values ('Dennis', 'P', 'Wright', '223233234', 
                             '16-DEC-69', '321 Main, Spring, TX', 'M', 85000, 
                             888665555, 1);
INSERT into EMPLOYEE Values ('April', 'E', 'Friedman', '543543543', 
                             '3-JAN-57', '4436 Cherry, Houston, TX', 'F', 
                             65000, 987654321, 4);
INSERT into EMPLOYEE Values ('Angela', 'J', 'Blanchette', '999227777', 
                             '16-FEB-65', '25 Elm, Houston, TX', 'F', 
                             68000, 333445555, 5);


INSERT into DEPARTMENT Values ('Research',       5, '333445555', '22-MAY-88');
INSERT into DEPARTMENT Values ('Administration', 4, '987654321', '01-JAN-95');
INSERT into DEPARTMENT Values ('Headquarters',   1, '888665555', '19-JUN-81');


INSERT into DEPT_LOCATIONS Values (1, 'Houston');
INSERT into DEPT_LOCATIONS Values (4, 'Stafford');
INSERT into DEPT_LOCATIONS Values (5, 'Bellaire');
INSERT into DEPT_LOCATIONS Values (5, 'Sugarland');
INSERT into DEPT_LOCATIONS Values (5, 'Houston');


INSERT into WORKS_ON Values ('123456789',  1, 32.5);
INSERT into WORKS_ON Values ('123456789',  2,  7.5);
INSERT into WORKS_ON Values ('666884444',  1, 25.0);
INSERT into WORKS_ON Values ('666884444',  3, 15.0);
INSERT into WORKS_ON Values ('453453453',  1, 10.0);
INSERT into WORKS_ON Values ('453453453',  2, 30.0);
INSERT into WORKS_ON Values ('333445555',  2, 10.0);
INSERT into WORKS_ON Values ('333445555',  3, 10.0);
INSERT into WORKS_ON Values ('333445555', 10, 10.0);
INSERT into WORKS_ON Values ('333445555', 20, 10.0);
INSERT into WORKS_ON Values ('999887777', 30, 30.0);
INSERT into WORKS_ON Values ('999887777', 10, 10.0);
INSERT into WORKS_ON Values ('123123123',  1, 35.0);
INSERT into WORKS_ON Values ('123123123',  2,  5.0);
INSERT into WORKS_ON Values ('987987987', 10, 35.0);
INSERT into WORKS_ON Values ('987987987', 30,  5.0);
INSERT into WORKS_ON Values ('987654321', 30, 20.0);
INSERT into WORKS_ON Values ('987654321', 20, 15.0);
INSERT into WORKS_ON Values ('888665555', 20, null);
INSERT into WORKS_ON Values ('999227777', 1,  12.0);
INSERT into WORKS_ON Values ('999227777', 3,  28.0);


INSERT into PROJECT Values ('ProductX',         1, 'Bellaire',  5);
INSERT into PROJECT Values ('ProductY',         2, 'Sugarland', 5);
INSERT into PROJECT Values ('ProductZ',         3, 'Houston',   5);
INSERT into PROJECT Values ('Computerization', 10, 'Stafford',  4);
INSERT into PROJECT Values ('Reorganization',  20, 'Houston',   1);
INSERT into PROJECT Values ('Newbenefits',     30, 'Stafford',  4);


INSERT into DEPENDENT Values ('333445555', 'Alice',     'F', '05-APR-86', 
                              'Daughter');
INSERT into DEPENDENT Values ('333445555', 'Theodore',  'M', '25-OCT-83', 
                              'Son');
INSERT into DEPENDENT Values ('333445555', 'Joy',       'F', '03-MAY-58', 
                              'Spouse');
INSERT into DEPENDENT Values ('987654321', 'Abner',     'M', '28-FEB-42', 
                              'Spouse');
INSERT into DEPENDENT Values ('123456789', 'Michael',   'M', '04-JAN-88', 
                              'Son');
INSERT into DEPENDENT Values ('123456789', 'Alice',     'F', '30-DEC-88', 
                              'Daughter');
INSERT into DEPENDENT Values ('123456789', 'Elizabeth', 'F', '05-MAY-67', 
                              'Spouse');



ALTER TABLE EMPLOYEE ADD CONSTRAINT 
	fk_employee_to_super FOREIGN KEY (SUPERSSN) REFERENCES EMPLOYEE(SSN)
;

ALTER TABLE EMPLOYEE ADD CONSTRAINT 
	fk_employee_to_dept FOREIGN KEY (DNO) REFERENCES DEPARTMENT(DNUMBER)
;

ALTER TABLE DEPARTMENT ADD CONSTRAINT 
	fk_dept_to_emp FOREIGN KEY (MGRSSN) REFERENCES EMPLOYEE(SSN)
;

ALTER TABLE DEPT_LOCATIONS ADD CONSTRAINT 
	fk_deptloc_to_dept FOREIGN KEY (DNUMBER) REFERENCES DEPARTMENT(DNUMBER)
;

ALTER TABLE PROJECT ADD CONSTRAINT 
	fk_proj_to_dept FOREIGN KEY (DNUM) REFERENCES DEPARTMENT(DNUMBER)	
;

ALTER TABLE WORKS_ON ADD CONSTRAINT 
	fk_workson_to_emp FOREIGN KEY (ESSN) REFERENCES EMPLOYEE(SSN)
;

ALTER TABLE WORKS_ON ADD CONSTRAINT 
	fk_workson_to_proj FOREIGN KEY (PNO) REFERENCES PROJECT(PNUMBER)
;

ALTER TABLE DEPENDENT ADD CONSTRAINT 
	fk_dep_to_emp FOREIGN KEY (ESSN) REFERENCES EMPLOYEE(SSN)	
;


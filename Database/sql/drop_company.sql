-- ========================================
-- 
--  File: 
--    ~eai/oracle/company/drop_company.sql
--
--  This file contains the drop table statements for the Company database
--  example shown in Fig. 5.6, _Fundamentals of Database Systems_ (5th ed.),
--  Elmasri and Navathe, 2006.
-- 
-- ========================================


ALTER TABLE EMPLOYEE DROP CONSTRAINT fk_employee_to_super;
ALTER TABLE EMPLOYEE DROP CONSTRAINT fk_employee_to_dept;
ALTER TABLE DEPARTMENT DROP CONSTRAINT fk_dept_to_emp;
ALTER TABLE DEPT_LOCATIONS DROP CONSTRAINT fk_deptloc_to_dept;
ALTER TABLE PROJECT DROP CONSTRAINT fk_proj_to_dept;
ALTER TABLE WORKS_ON DROP CONSTRAINT fk_workson_to_emp;
ALTER TABLE WORKS_ON DROP CONSTRAINT fk_workson_to_proj;
ALTER TABLE DEPENDENT DROP CONSTRAINT fk_dep_to_emp;

DROP TABLE EMPLOYEE;
DROP TABLE DEPARTMENT;
DROP TABLE DEPT_LOCATIONS;
DROP TABLE PROJECT;
DROP TABLE WORKS_ON;
DROP TABLE DEPENDENT;

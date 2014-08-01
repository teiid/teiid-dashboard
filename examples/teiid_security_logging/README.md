README for using Teiid Security Logging Dashboard Example	
==========================

This example is created to be used when you have installed the command / audit logging Teiid extension for logging to a database.

Included in this example are the following files:
-  workspace.cex  :  TeiidLogging workspace
-  kpiLogging.xml :  Audit and Command Logging KPI's for viewing and filtering logs

===============================
Prerequisites
===============================
1.  Need to deploy the teiid-dashbuilder-jboss-as7.war to a Teiid server to a JBoss AS server

A.  Look at the following file [builder/README.md](https://github.com/teiid/teiid-dashboard/blob/master/builder/README.md)
	for building and deploying the war.
B.  Once the war is deployed, verify you can log in as an administrator (with "admin" role) user and as a teiid (with "user" role) user



===============================
Example Steps:  The following steps will be performed as part of setting up and using this example.
===============================
1.  Log into dashboard as admin
2.  Create datasource, name is important, and needs to be added before import steps
3.  Import workspace
4.  Import dashboard
5.  Log out, and then login as teiid user to view logging


===============================
Steps:
===============================
(1).  Log into dashboard as admin user

-  use this URL to access the dashboard:  http://{host}:8080/dashboard

(2)  Add Data Source

-  select Administration --> External connections

---  click "Create new DataSource" to create a new data source
---  select "JNDI" to present the present the panel to enter the connection information
---  Name is  TeiidLogging  (NOTE:  Name is important, because its the name used when the Source Queries were created, otherwise, 
			a NPE happens when you select a Source Query and the data source doesn't exist
---  the JNDI path:  java:/teiid-log-ds
---  Test Query:  SELECT * FROM COMMANDLOG where ID=1

Press "Check DatdaSource" button to verify settings, should get a message indicates all is good
Press "Save" to keep the settings


(3).  Import workspace

click on Administration --> Import and export  to display options

click on Export/Import Workspaces tab
On the right side, in the "Import" panel, click "Choose File" button
--- browse to find the workspace.cex file to import
--- select workspace.cex file and click Open
--- click Import button, will be presented panel to select content
--- click WORKSPACE: 0 (if not already selected) and then press Import
--- should see message "Successfully created workspace"

(4)  Import KPI dashboard

On the same page where you imported workspace, on the bottom panel, you will find Import Dashboards, select the "Choose File" button 
to find the dashboard
--- browse to the find the kpilogging.xml file
--- select the kpilogging.xml file and click Open (returns to the original starting panel)
--- press the Import button, and should see the following messages:

BAM_00000 - Data provider created. dataprovider_84341401722402006, AuditLoggingTable.
BAM_00000 - Data provider created. dataprovider_10951401722888067, Command Logging.
BAM_00001 - Business indicator created. kpi_16821401722967869, Command Log Contents.
BAM_00001 - Business indicator created. kpi_16671401722531063, Logging Contents. 


(5)  Log out as admin, and log back in as a Teiid user.

You should see under Home,  Audit Logging and Command Logging.


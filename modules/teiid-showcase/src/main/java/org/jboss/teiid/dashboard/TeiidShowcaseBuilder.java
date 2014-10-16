/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.teiid.dashboard;

import java.util.List;

import org.jboss.dashboard.CoreServices;
import org.jboss.dashboard.annotation.Priority;
import org.jboss.dashboard.annotation.Startable;
import org.jboss.dashboard.annotation.config.Config;
import org.jboss.dashboard.database.DataSourceManager;
import org.jboss.dashboard.database.JNDIDataSourceEntry;
import org.jboss.dashboard.error.TechnicalError;
import org.jboss.dashboard.factory.InitialModuleRegistry;
//import org.jboss.dashboard.initialModule.InitialModuleRegistry;
import org.jboss.dashboard.kpi.KPIInitialModule;
import org.jboss.dashboard.workspace.export.ImportWorkspacesModule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Class that builds the Teiid Dashboard Builder's showcase on startup.
 */
@ApplicationScoped
public class TeiidShowcaseBuilder implements Startable {
	
	private static final String TEIID_LOGGING_NAME = "TeiidLogging";
	private static final String JNDI_PATH = "java:/teiid-log-ds";
	private static final String TEST_QUERY = "SELECT * FROM COMMANDLOG where ID=1";


    @Inject
    private InitialModuleRegistry initialModuleRegistry;

    @Inject @Config("WEB-INF/etc/appdata/initialData/showcaseKPIs.xml")
    private String kpiFile;

    @Inject @Config("WEB-INF/etc/appdata/initialData/showcaseWorkspace.xml")
    private String workspaceFile;
    
    @Inject @Config("WEB-INF/etc/appdata/initialData/kpiLogging.xml")
    private String loggingkpiFile;

    @Inject @Config("WEB-INF/etc/appdata/initialData/loggingWorkspace.xml")
    private String loggingworkspaceFile;
   
    private DataSourceManager dataSourceManager;


    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void start() throws Exception {
    	dataSourceManager = CoreServices.lookup().getDataSourceManager();
    	
    	if (dataSourceManager==null) throw new TechnicalError("Program Error: DataSourceManager was not obtained in order to check for TeiidLogging", null);
             	
        KPIInitialModule kpis = new KPIInitialModule();
        kpis.setName("org.jboss.teiid.dashboard.showcase.KPIs");
        kpis.setImportFile(kpiFile);
        kpis.setVersion(1);
        initialModuleRegistry.registerInitialModule(kpis);

        ImportWorkspacesModule workspace = new ImportWorkspacesModule();
        workspace.setName("org.jboss.teiid.dashboard.showcase.Workspace");
        workspace.setImportFile(workspaceFile);
        workspace.setVersion(1);
        initialModuleRegistry.registerInitialModule(workspace);
        
        // LOGGING Workspace and KPI
    	boolean loggingExist = checkExist(JNDI_PATH);
    	if (loggingExist) {
	    	checkAndCreateDatasources();
	    
	
	        KPIInitialModule lkpis = new KPIInitialModule();
	        lkpis.setName("org.jboss.teiid.dashboard.showcase.LoggingKPIs");
	        lkpis.setImportFile(loggingkpiFile);
	        lkpis.setVersion(1);
	        initialModuleRegistry.registerInitialModule(lkpis);
	
	        ImportWorkspacesModule lworkspace = new ImportWorkspacesModule();
	        lworkspace.setName("org.jboss.teiid.dashboard.showcase.LoggingWorkspace");
	        lworkspace.setImportFile(loggingworkspaceFile);
	        lworkspace.setVersion(1);
	        initialModuleRegistry.registerInitialModule(lworkspace);   	
    	}
    }
    
    protected void checkAndCreateDatasources() throws Exception {
    	List<String> names = dataSourceManager.getDataSourceNames();
    	if (names != null && names.contains(TEIID_LOGGING_NAME)) {
    		
    	} else {
        	JNDIDataSourceEntry jndi = new JNDIDataSourceEntry();
        	jndi.setJndiPath(JNDI_PATH);
        	jndi.setTestQuery(TEST_QUERY);
        	jndi.setName(TEIID_LOGGING_NAME);
        	
        	jndi.save();
        }
    } 
    
    private boolean checkExist(String jndi) {
   	 try {
			InitialContext ctx = new InitialContext();  
			return (ctx.lookup(jndi) != null ? true : false); //$NON-NLS-1$
		} catch (NamingException e) {
		}      	
		return false;
    }
}

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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.dashboard.annotation.Priority;
import org.jboss.dashboard.annotation.Startable;
import org.jboss.dashboard.annotation.config.Config;
import org.jboss.dashboard.export.ImportResults;
import org.jboss.dashboard.factory.InitialModuleRegistry;
//import org.jboss.dashboard.kpi.KPI;
import org.jboss.dashboard.kpi.TeiidKPIInitialModule;
import org.jboss.dashboard.provider.DataProvider;
import org.jboss.dashboard.workspace.export.TeiidImportWorkspacesModule;

/**
 * Class that builds the Teiid Dashboard Builder's showcase on startup.
 */
@ApplicationScoped
public class TeiidShowcaseBuilder implements Startable {
	
    /** Logger */
    private static transient org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(TeiidShowcaseBuilder.class.getName());

	
	private static final String JBOSS_BASE_DIR = "jboss.server.base.dir";
	private static final String TEIID_DASHBUILDER_DIR = "../dataServices/teiidDashbuilder";
	
//	public static final String KPI_DIRECTORY = "dashboard.kpi.directory";
//	public static final String WORKSPACE_DIRECTORY = "dashboard.workspace.directory";

    @Inject
    private InitialModuleRegistry initialModuleRegistry;

    @Inject @Config("WEB-INF/etc/appdata/initialData/teiidShowcaseKPIs.xml")
    private String kpiFile;

    @Inject @Config("WEB-INF/etc/appdata/initialData/teiidShowcaseWorkspace.xml")
    private String workspaceFile;
    
    
    private String jbossServerBaseDir = System.getProperty(JBOSS_BASE_DIR);
    

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void start() throws Exception {
    	boolean loadedKPIFiles = false;
    	boolean loadedWorkspaceFiles = false;
    
    	
//    	loadWorkspace(workspaceFile, "org.jboss.teiid.dashboard.showcase.Workspace"); 
    	loadKPI(kpiFile, "org.jboss.teiid.dashboard.showcase.KPIs");
    	
		if (jbossServerBaseDir != null) {
    		String kpiPath = jbossServerBaseDir + File.separator + TEIID_DASHBUILDER_DIR + File.separator + "kpiFiles";		
    		
    		File pkiFile = new File(kpiPath);
    		if (pkiFile.exists()) {
    			List<File> files = this.getFilesInDirectory(pkiFile, "xml");
    	        if (files != null && ! files.isEmpty()) {
    	        	loadedKPIFiles = true;
    	        	for (File f : files ) {
    	        		
    	        		loadKPI(f.getAbsolutePath(), "org.jboss.teiid.dashboard.showcase.KPI." + f.getName());
    	        	}
    	        } else {
    	        	log.info("The directory " + kpiPath + " does not contain any KPI files.");
    	        }
    			
    		}
    		
//    		if (!loadedKPIFiles) {
//    			loadKPI(kpiFile, "org.jboss.teiid.dashboard.showcase.KPIs");
//    		}
    		
    		
    		String wsPath = jbossServerBaseDir + File.separator + TEIID_DASHBUILDER_DIR + File.separator + "workspaceFiles";		
    		
    		File wsFile = new File(wsPath);
    		if (wsFile.exists()) {
    			List<File> files = this.getFilesInDirectory(wsFile, "cex");
    	        if (files != null && ! files.isEmpty()) {
    	        	loadedWorkspaceFiles = true;
    	        	for (File f : files ) {
    	        		loadWorkspace(f.getAbsolutePath(), "org.jboss.teiid.dashboard.showcase.Workspace." + f.getName());
    	        	}
    	        } else {
    	        	log.info("The directory " + wsPath + " does not contain any Workspace files.");
    	        }
    			
    		}
    		if (!loadedWorkspaceFiles) {
    			loadWorkspace(workspaceFile, "org.jboss.teiid.dashboard.showcase.Workspace");   			
    		}
    	}
		
		
    }
    
    private void loadKPI(String path, String name) {
        TeiidKPIInitialModule kpis = new TeiidKPIInitialModule();
        kpis.setName(name);
        kpis.setImportFile(path);
        kpis.setVersion(1);
        initialModuleRegistry.registerInitialModule(kpis);
        
        log.info("Loaded KPI file " + path + " into dashboard.");
    }
    
    private void loadWorkspace(String path, String name) {
        TeiidImportWorkspacesModule workspace = new TeiidImportWorkspacesModule();
        workspace.setName(name);
        workspace.setImportFile(path);
        workspace.setVersion(1);
        initialModuleRegistry.registerInitialModule(workspace);
        
        log.info("Loaded Workspace file " + path + " into dashboard.");
	
    }
        
	    private List<File> getFilesInDirectory(File path, String ext) {
	    		    
	        if (!path.exists()) {
	            log.error("The directory " + path+ " does not exist.");
	            return null;
	        }
	        List<File> files = new ArrayList<File>();
	        if (path.isDirectory()) {
	        	   
		        File[] sourceFiles = path.listFiles();
		        for (int i = 0; i < sourceFiles.length; i++) {
		            File srcFile = sourceFiles[i];
		            if (!srcFile.isDirectory() && srcFile.getName().endsWith(ext)) {
		            		files.add(srcFile);
		            }
		        }
		               
	        } 
	        return files;

	    }
}

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
package org.jboss.dashboard.workspace.export;

import org.jboss.dashboard.Application;
import org.jboss.dashboard.factory.InitialModule;
import org.jboss.dashboard.ui.UIServices;
import org.jboss.dashboard.workspace.export.structure.ImportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class TeiidImportWorkspacesModule extends InitialModule {

    private static transient Logger log = LoggerFactory.getLogger(TeiidImportWorkspacesModule.class.getName());

    /**
     * Represents the path of the XML file to import. A relative path to the application directory.
     */
    protected String importFile;

    public String getImportFile() {
        return importFile;
    }

    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    public boolean upgrade(long l) {
        return false;
    }

    public boolean install() {
        try {
        	
            File pf = new File(importFile);
            if (!pf.exists()) {
 
	            // Get the XML file.
	            Application cm = Application.lookup();
	            pf = new File(cm.getBaseAppDirectory() + File.separator + importFile);
	            if (!pf.exists()) {
	                log.error("Cannot find file " + importFile + " for Workspace initial module.");
	                return false;
	            }
            }
            
            log.info("Parsing Workspace file: " + importFile);

            // Load the file.
            ExportManager exportManager = UIServices.lookup().getExportManager();

            if (importFile.endsWith("cex")) {
            	ImportResult[] resultx = exportManager.load( new FileInputStream(pf));
            	
            	for (ImportResult r:resultx) {
            		boolean rtn = loadResult(exportManager, r);
            		if (!rtn) return rtn;	
            	}
            	return true;
            } else {
            	ImportResult result = exportManager.loadXML(pf.getName(), new FileInputStream(pf));
            	return loadResult(exportManager, result);
            }
//            if (result == null) {
//                log.warn("Error on importation. Nothing to import");
//                return false;
//            }
//
//            if (result.getException() != null) throw result.getException();
//            if (result.getWarnings() != null && result.getWarnings().size() > 0) {
//                for (int j = 0; j < result.getWarnings().size(); j++) {
//                    log.warn("Problems importing entry " + result.getEntryName() + ": " + result.getWarnings().get(j));
//                }
//            }
//
//            exportManager.create(new ImportResult[]{result}, null, true);
//            return true;
        } catch (Exception e) {
            log.error("Error importing module " + getName() + " version " + getVersion(), e);
        }
        return false;
    }
    
    private boolean loadResult(ExportManager exportManager, ImportResult result) throws Exception {
        if (result == null) {
            log.warn("Error on importation. Nothing to import");
            return false;
        }

        if (result.getException() != null) throw result.getException();
        if (result.getWarnings() != null && result.getWarnings().size() > 0) {
            for (int j = 0; j < result.getWarnings().size(); j++) {
                log.warn("Problems importing entry " + result.getEntryName() + ": " + result.getWarnings().get(j));
            }
        }

        exportManager.create(new ImportResult[]{result}, null, true);
        return true;	
    }
}

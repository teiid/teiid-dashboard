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
package org.jboss.dashboard.kpi;

import org.jboss.dashboard.DataDisplayerServices;
import org.jboss.dashboard.LocaleManager;
import org.jboss.dashboard.Application;
import org.jboss.dashboard.factory.InitialModule;
import org.jboss.dashboard.export.ImportManager;
import org.jboss.dashboard.export.ImportResults;
import org.jboss.dashboard.commons.message.Message;
import org.jboss.dashboard.commons.message.MessageList;


import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Locale;

/**
 * KPI initial module class. It makes possible to install KPIs and data providers at start-up.
 */
public class TeiidKPIInitialModule extends InitialModule {

    /** Logger */
    private static transient org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TeiidKPIInitialModule.class.getName());

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

    protected boolean install() {
        return installOrUpgrade(false);
    }

    protected boolean upgrade(long currentVersion) {
        long newVersion = getVersion();
        if (newVersion <= currentVersion) return false;

        return installOrUpgrade(true);
    }

    protected boolean installOrUpgrade(boolean upgrade) {
        try {
            if (!check()) return false;

            File pf = new File(importFile);
            if (!pf.exists()) {
            // Get the XML file.

	            Application cm = Application.lookup();
	            pf = new File(cm.getBaseAppDirectory() + File.separator + importFile);
	            if (!pf.exists()) {
	                log.error("Cannot find file " + importFile + " for KPI initial module.");
	                return false;
	            }
     
            }
            
            log.info("Parsing KPI's XML file: " + importFile);

            // Parse the file.
            ImportManager importMgr = DataDisplayerServices.lookup().getImportManager();
            ImportResults importResults = importMgr.parse(new FileInputStream(pf));

            // Save the imported results.
            if (upgrade) importMgr.update(importResults);
            else importMgr.save(importResults);

            // Show import messages.
            MessageList messages = importResults.getMessages();
            Locale locale = LocaleManager.currentLocale();
            Iterator it = messages.iterator();
            while (it.hasNext()) {
                Message message = (Message) it.next();
                switch (message.getMessageType()) {
                    case Message.ERROR: log.error(message.getMessage(locale)); break;
                    case Message.WARNING: log.warn(message.getMessage(locale)); break;
                    case Message.INFO: log.info(message.getMessage(locale)); break;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Error importing KPIs file (" + importFile + ") from initial module.", e);
            return false;
        }
    }

    /**
     * Check if file exists.
     */
    protected boolean check() {
        boolean correct = true;
        if (importFile == null) {
            log.error("Error. Import file not defined for initial module.");
            correct = false;
        }
        return correct;
    }
}

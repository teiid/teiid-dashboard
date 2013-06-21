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
package org.jboss.teiid.dashboard.database;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.jboss.dashboard.annotation.Priority;
import org.jboss.dashboard.annotation.Startable;
import org.jboss.dashboard.database.DataSourceManager;
import org.jboss.dashboard.database.JDBCDataSourceEntry;
import org.teiid.jdbc.TeiidDriver;

/**
 * Creates (if doesn't exist) a default Dashbuilder's data source connection against Teiid.
 */
@ApplicationScoped
public class TeiidDataConnectionBuilder implements Startable {

    public static final String TEIID_DATA_SOURCE_EXAMPLE = "Portfolio";
    
    @Inject
    protected DataSourceManager dataSourceManager;

    public Priority getPriority() {
        return Priority.LOW;
    }
    
    public void start() throws Exception {
    	
        DataSource teiidDS = dataSourceManager.getDataSource(TEIID_DATA_SOURCE_EXAMPLE);
        if (teiidDS == null) {
            JDBCDataSourceEntry jdbcDS = new JDBCDataSourceEntry();
            jdbcDS.setName(TEIID_DATA_SOURCE_EXAMPLE);
            jdbcDS.setDriverClass(TeiidDriver.class.getName());
            jdbcDS.setUrl("jdbc:teiid:Portfolio");
            jdbcDS.setUserName("user");
            jdbcDS.setPassword("user");
            jdbcDS.save();

//        	
//        	
//            JNDIDataSourceEntry jdbcDS = new JNDIDataSourceEntry();
//            jdbcDS.setName(TEIID_DATA_SOURCE_EXAMPLE);
//            jdbcDS.setJndiPath("java:/Portfolio");
//            jdbcDS.setUserName("user");
//            jdbcDS.setPassword("user");
//            jdbcDS.setTestQuery("SELECT 1");
//            jdbcDS.save();
        }
    }
}

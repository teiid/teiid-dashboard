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

import org.jboss.dashboard.database.DataSourceManager;

import javax.enterprise.inject.Specializes;
import javax.sql.DataSource;

/**
 * Specialized version of the Dashbuilder's DataSourceManager which adds custom Teiid connectivity stuff.
 */
@Specializes
public class TeiidDataSourceManager extends DataSourceManager {

    public DataSource getDatasource(String name) throws Exception {
        DataSource ds = super.getDatasource(name);
        if (ds == null) return null;

        return new TeiidWrapperDataSource(ds);
    }
}

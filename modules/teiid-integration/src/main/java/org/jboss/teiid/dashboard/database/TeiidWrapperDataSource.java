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

import org.jboss.dashboard.users.UserStatus;
import org.teiid.jdbc.TeiidConnection;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Data source specialized in TeiidConnection handling.
 */
public class TeiidWrapperDataSource implements DataSource {

    protected DataSource rootDataSource;

    public TeiidWrapperDataSource(DataSource rootDataSource) {
        this.rootDataSource = rootDataSource;
    }

    public Connection processConnection(Connection conn) throws SQLException {
        try {
            // Catch Teiid connections
            TeiidConnection teiidConnection = (TeiidConnection) conn;

            // Pass the current logged user to Teiid every time a connection is requested.
            UserStatus userStatus = UserStatus.lookup();
            String userLogin = userStatus.getUserLogin();
            teiidConnection.changeUser(userLogin, null);
            return teiidConnection;
        } catch (ClassCastException e) {
            return conn;
        }
    }

    public Connection getConnection() throws SQLException {
        return processConnection(rootDataSource.getConnection());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return processConnection(rootDataSource.getConnection(username, password));
    }

    public PrintWriter getLogWriter() throws SQLException {
        return rootDataSource.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        rootDataSource.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        rootDataSource.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return rootDataSource.getLoginTimeout();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return rootDataSource.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return rootDataSource.isWrapperFor(iface);
    }
}

Deployment onto JBoss
==========================

The <code>jbossas7</code> directory contains all the artifacts to build the distribution for the JBoss AS 7.0 and 7.1 server.

Please follow the next steps in order to deploy the application.

Generate a distribution
---------------------------

Open a terminal window, go to the root directory and execute the following Maven command:

    $ mvn clean install -Dfull  -DskipTests

Go to the <code>builder/target</code> directory and get the file called **teiid-dashbuilder-jboss-as7.war**.

Deploy the war
----------------------------

Get the proper WAR file (e.g. teiid-dashbuilder-jboss-as7.war) and execute the following command:

    $ cd <jboss_home>/bin
    $ ./jboss-cli.sh --connect command="deploy <path_to_war_file>"

    <path_to_war_file>: is the local path to the application war file.
    e.g. $ ./jboss-cli.sh --connect --command="deploy /home/myuser/myfiles/teiid-dashbuilder-jboss-as7.war" )

To setup the H2 datasource so that your dashboard additions/changes will be persisted and available on server restart, 
run the CLI script: builder/src/main/scripts/add_datasource.cli.  Execute the following:

    $ cd <jboss_home>/bin
    $ ./jboss-cli.sh --connect file="<path_to_cli_script_file>"

The application is configured to to look for following JNDI name: <code>java:jboss/datasources/DashboardDS</code>.  If
the datasource does not exist, the dashboard builder will just operate in in-memory mode.

Notice, this datasource is intended for development/demo purposes and it's present by default at any JBoss installation.

If you want to deploy on a database different from H2 like Oracle, MySQL, Postgres or MS SQL Server please follow the next steps:

* Install the database driver on JBoss (read the JBoss documentation)

* Create an empty database and a JBoss data source which connects to it

* Modify the file *teiid-dashboard/builder/src/main/jbossas7/WEB-INF/jboss-web.xml*:

        <jboss-web>
           <context-root>/teiid-dashboard</context-root>
           <resource-ref>
               <res-ref-name>jdbc/teiid-dashboard</res-ref-name>
               <res-type>javax.sql.DataSource</res-type>
               <jndi-name>java:jboss/datasources/DashboardDS</jndi-name>
           </resource-ref>
           ...

   Replace the *jndi-name* parameter value by the JNDI path of the JBoss data source you've just created.

* Modify the file *teiid-dashboard/builder/src/main/jbossas7/WEB-INF/jboss-deployment-structure.xml*.

  Add the following snippet of configuration inside the *deployment* tag, where *jdbcDriverModuleName* is the name of the JBoss JDBC driver module.

        <dependencies>
            <module name="jdbcDriverModuleName" />
        </dependencies>


After that, your are ready to generate a WAR distribution prepared for the target database.

User Authentication
--------------------------

NOTE:  The security domain is set to teiid-security, which is the same security domain that Teiid defaults to.
       Therefore, any user setup (i.e., add/update/delete) will need to done to this domain.
    

Once started, open a browser and type the following URL:
<code>http://localhost:8080/teiid-dashboard</code>. A login screen should be displayed.

However, some extra configuration is needed before you can sign in:

* The application is based on the J2EE container managed authentication  mechanism.
This means that the login itself is delegated to the application server.

* First of all, in order to login as superuser, you must create a user with login=<code>root</code> and 
role=<whatever role has been defined in the web.xml file>. This is just for container authentication purposes, as 
the root user's application privileges are not role-linked, but instead is granted with all permissions).

* The application roles are defined at [builder/src/main/jbossas7/WEB-INF/web.xml](https://github.com/teiid/teiid-dashboard/blob/master/builder/src/main/jbossas7/WEB-INF/web.xml) file.
Roles can be used to create access profiles and define custom authorization policies.

NOTE: these roles are not the same roles defined in the teiid-security domain.  Even though some maybe named the same, 
there's no implied cross over in access permissions.

* The application uses Teiids' default security domain (e.g., teiid-security) as you can see [here](https://github.com/teiid/teiid-dashboard/blob/master/builder/src/main/jbossas7/WEB-INF/jboss-web.xml).
Alternatively, you can define your own security domain and use, for instance, an LDAP, a database, or whatever mechanism you want to use as your credential storage.
There are plenty of examples in the JBoss AS documentation about.

Feel free to change any settings regarding the application security and, once finished, to generate a distribution war that fits your needs.

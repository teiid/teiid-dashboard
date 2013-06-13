Teiid Dashboard Builder
=======================

Teiid Dashboard Builder is a web for the visual composition of dashboards. A dashboard is composed by a set of
 business indicators (aka Key Performance Indiocators) which feed from data coming from Teiid virtual databases.

The project itself is a customization of the [Dashboard Builder](https://github.com/droolsjbpm/dashboard-builder) project
Such customization has the following goals in mind:

* To replace the whole tooling look&feel with Teiid's branding.
* To provide users with a set out-of-the-box sample dashboards to demonstrate how to exploit data coming from Teiid vistual databases.
* To offer a tight integration between the Dashbuilder and Teiid data connectivity layer.

If you discover pitfalls, tips and tricks not described in this document,
please update it using the [markdown syntax](http://daringfireball.net/projects/markdown/syntax).

Quickstart
-------------------

If you want to build the project and execute a quick demo, please, proceed as indicated:

1. Prerequisites:

  This guide assumes you have Java JDK 1.6 (set as JAVA_HOME), and Maven 3.0.4+ (set as MAVEN_HOME) in your system.
  The <code>java</code> and <code>mvn</code> commands must be added to the executable's path as well.

2. Open a terminal window, go to the project root directory and type the following command:

        $ mvn clean install -Dfull -DskipTests

  This command compiles, builds and runs the application. It'll last a few minutes (but only for the first time) because
  the maven build process needs to download a lot of third-party libraries.

3. Go to the <code>builder/target</code> directory and unzip the teiid-dashbuilder-demo-installer.zip file to
  a given directory (the [target_directory]). You should get a directory structure like this:

         [target_directory]/teiid-dashboard-demo
                              README.md
                              start-demo.sh
                              realm.properties
                              /db
                              /log

4. Open a command window and execute the <code>start-demo.sh</code> script:

        $ cd [target_directory]/teiid-dashbuilder-demo
        $ sh start-demo.sh

  NOTE: The application uses an auto-deployable embedded H2 database which it's automatically created when you start
  the app for the very first time. The database initialization procedure it takes a few minutes. Furthermore, you should
  take into account that the H2 database downgrades the application performance compared with other databases like
  PostgreSQL, MySQL, normally used in production environments.

5. Once the application is started, open a browser and type the URL: <code>http://localhost:8080/teiid-dashboard</code>.
The following user/password are available by default:

     <code>root/root</code>: to sign-in as the superuser     
     <code>demo/demo</code>: to sign-in as an end user

  On start-up, the application installs automatically some ready-to-use sample dashboards, for demo and learning purposes.

  To stop the application close the terminal window or type the "Ctrl + C" command.

6. Application database

The application database will be generated automatically when you start the application for the first time.
If you want to restore the application to its initial state you can:

* Stop the application (if running).
* Delete the database files in the <code>/db</code> directory.
* Start the application.

JBoss Application Server
-------------------------

If you want to generate a distribution & deploy it onto the JBoss Application Server take a look at the
following file [builder/README.md](https://github.com/teiid/teiid-dashboard/blob/master/builder/README.md).

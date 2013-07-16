Teiid Dashboard Builder Demo
=============================

The following directories contain the needed files to run the Teiid Dashboard Builder application in standalone demo mode.
Feel free to run and modify this installation as much as you wish, but remember that's NOT
RECOMMENDED to use this demo in production environments.


Prerequisites
-------------------

The only prerequisite to run the demo application is to have the JDK 1.6 installed in your system.

If you don't have the JDK 1.6 installed in your system please use the following [link]
(http://www.oracle.com/technetwork/java/javase/downloads/index.html) to download the JDK and get
the installation instructions. (go the "Java SE 6 Update XX" section to select the last available update)

Installation steps
------------------

* 1 Check your JDK installation:

  Open a command window a type the following command:

    $ java -version

* If you have the JDK installed in your system you will see a command output like this:

        "java version "1.6.0_35"
        Java(TM) SE Runtime Environment (build 1.6.0_35-b10)
        Java HotSpot(TM) 64-Bit Server VM (build 20.10-b01, mixed mode)"

* Set the JAVA_HOME environment variable pointing to the JDK installation directory.

        $ export JAVA_HOME=/usr/java/jdk1.6.0

* Unzip the dashbuilder-demo-installer.zip file to a given directory (the [target_directory]).
  You should get a directory structure like this:

         [target_directory]/teiid-dashbuilder-demo
                              README.md
                              start-demo.sh
                              realm.properties
                              /db

* Open a command window and execute the start-demo.sh script (for linux environments)

        $ cd <target_directory>/teiid-dashbuilder-demo
        $ ./start-demo.sh

  NOTE: The application uses an auto-deployable embedded H2 database which it's automatically created when you start
  the app for the very first time. The database initialization procedure it takes a few minutes. Furthermore, you should
  take into account that the H2 database downgrades the application performance compared with other databases like
  PostgreSQL, MySQL, normally used in production environments.

* Once the application is started, open a browser and type the URL: <code>http://localhost:8080/teiid-dashboard</code>.
The following user/password are available by default:

    * <code>root/root</code>: to sign-in as the superuser
    * <code>demo/demo</code>: to sign-in as an end user.

  On start-up, the application installs automatically some ready-to-use sample dashboards, for demo and learning purposes.

* To stop the application close the terminal window or type the "Ctrl + C" command.


Application database
----------------------

The application database will be generated automatically when you start the application for the first time.
If you want to restore the application to its initial state you can:

1. Stop the application (if running).
2. Delete the database files in the <code>/db</code> directory.
3. Start the application.

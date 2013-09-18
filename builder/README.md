Distribution Builder
==========================

The 'builder' project generates the product distribution for different applications servers as well as
the auto-instalable standalone demo.

If you want to generate all the distribution files then go to the teiid-dashboard root directory and
type the following command:

    $ mvn clean install -Dfull -DskipTests

Currently, the following artifacts are generated:

* **teiid-dashbuilder-jboss-as7.war:**  .war distribution for JBoss EAP 6.1 and JBoss AS 7.1 servers.

  By the default the distribution is configured to use H2 hibernate dialect but database drivers aren't packaged with the distribution.

  Read the [src/main/jbossas7/README.md](https://github.com/teiid/teiid-dashboard/blob/master/builder/src/main/jbossas7/README.md) file to get more information.

* **teiid-dashbuilder-demo-installer.zip:** Standalone demo.

  Read the [src/main/standalone-demo/README.md](https://github.com/teiid/teiid-dashboard/blob/master/builder/src/main/standalone-demo/README.md) file to get more information.

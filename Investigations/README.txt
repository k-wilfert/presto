TO BE ADDED TO THE ACTUAL DOCUMENTATION WEBSITE

JDBC Catalog

To use the Iceberg JDBC catalog, drop the connector JAR into plugin/iceberg-jdbc/, place your JDBC driver in plugin/iceberg-jdbc/lib/, and add a catalog properties file in etc/catalog/jdbc.properties.

Sample jdbc.properties:

connector.name=iceberg
iceberg.jdbc.uri=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
iceberg.jdbc.username=sa
iceberg.jdbc.password=
iceberg.jdbc.driver=org.h2.Driver
iceberg.jdbc.nested-namespace-enabled=true

How to start Presto with the JDBC catalog

Build your connector and copy it to the Presto plugin/iceberg-jdbc/ folder.

Copy jdbc.properties into etc/catalog.

Ensure the H2 (or your target database) driver JAR is in plugin/iceberg-jdbc/lib/.

Start Presto: bin/launcher start

Connect via CLI: presto-cli --catalog jdbc --schema public
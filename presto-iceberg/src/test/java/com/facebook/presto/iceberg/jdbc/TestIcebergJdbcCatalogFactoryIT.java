package com.facebook.presto.iceberg.jdbc;

import com.facebook.presto.testing.DistributedQueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.facebook.presto.iceberg.jdbc.IcebergJdbcCatalogModule.*;
import static org.junit.jupiter.api.Assertions.assertQuery;

public class TestIcebergJdbcCatalogFactoryIT
{
    private static DistributedQueryRunner runner;

    @BeforeAll
    public static void setUp() throws Exception
    {
        runner = DistributedQueryRunner.builder()
                .setNodeCount(1)
                .build();

        // install JDBC plugin
        runner.installPlugin(new IcebergJdbcPlugin());
        runner.createCatalog("jdbc", "jdbc", System.getProperties());

        // Bootstrap H2 database
        Class.forName("org.h2.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "")) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE test_table(x INT)");
                stmt.execute("INSERT INTO test_table VALUES (1),(2),(3)");
            }
        }
    }

    @AfterAll
    public static void tearDown()
    {
        runner.close();
    }

    @Test
    public void testSelectCount() throws Exception
    {
        runner.execute("SELECT count(*) FROM jdbc.test_table");
        assertQuery(runner, "SELECT count(*) FROM jdbc.test_table", "VALUES 3");
    }
}

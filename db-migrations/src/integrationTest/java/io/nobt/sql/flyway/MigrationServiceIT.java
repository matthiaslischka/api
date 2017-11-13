package io.nobt.sql.flyway;

import io.nobt.persistence.DatabaseConfig;
import io.nobt.test.persistence.PostgreSQLContainerDatabaseConfig;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

public class MigrationServiceIT {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6");

    private static DatabaseConfig databaseConfig;

    private MigrationService sut;

    @BeforeClass
    public static void setupEnvironment() {
        databaseConfig = new PostgreSQLContainerDatabaseConfig(postgreSQLContainer);
    }

    @Before
    public void setUp() throws Exception {
        sut = new MigrationService(databaseConfig);
    }

    @Test
    public void shouldPerformMigrationsWithoutError() throws Exception {
        sut.migrate();
    }

    @After
    public void tearDown() throws Exception {
        sut.clean();
    }
}
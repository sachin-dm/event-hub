package com.sbbs.tickets.eventhub.integration;

import com.sbbs.tickets.eventhub.repository.EventRepository;
import com.sbbs.tickets.eventhub.repository.UserRepository;
import com.sbbs.tickets.eventhub.service.EventService;
import com.sbbs.tickets.eventhub.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;

@ActiveProfiles(profiles = {"testx"})
@Tag("integration")
@CommonsLog
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
        BaseIntegrationTest.DockerPostgresDatasourceInitializer.class
})
public abstract class BaseIntegrationTest {

    static String jdbcUrl;
    static String username;
    static String password;

    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected EventService eventService;
    @Autowired
    protected UserService userService;

    @BeforeAll
    public void setup() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database!");

            // Load the SQL script
            InputStream inputStream = BaseIntegrationTest.class.getClassLoader().getResourceAsStream("event_schema.sql");
            if (inputStream == null) {
                throw new RuntimeException("SQL script not found in resources!");
            }

            StringBuilder sqlScript = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlScript.append(line).append("\n");
                }
            }

            // Execute the SQL script
            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlScript.toString());
                log.info("SQL script executed successfully!");
            }
        } catch (Exception e) {
            log.error("Failed to execute SQL script.: ",  e);
        }
    }

    static class DockerPostgresDatasourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14-alpine");
            postgreSQLContainer.start();

            jdbcUrl = postgreSQLContainer.getJdbcUrl();
            username = postgreSQLContainer.getUsername();
            password = postgreSQLContainer.getPassword();

            String[] properties = {
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            };

            log.info("PostgresSQLContainer connection properties: ");
            Arrays.stream(properties).sequential().forEach(log::info);
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext, properties);
        }
    }
}

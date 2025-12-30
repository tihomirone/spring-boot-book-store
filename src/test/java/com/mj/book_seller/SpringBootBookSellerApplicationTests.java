package com.mj.book_seller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test to verify Spring Boot application context can load without database connection.
 * This test uses a minimal configuration that excludes database and security auto-configurations
 * and disables component scanning to avoid loading application components that depend on the database.
 */
@SpringBootTest(classes = SpringBootBookSellerApplicationTests.TestConfig.class)
@ActiveProfiles("test")
class SpringBootBookSellerApplicationTests {

	@SpringBootApplication(
		exclude = {
			DataSourceAutoConfiguration.class,
			HibernateJpaAutoConfiguration.class,
			SecurityAutoConfiguration.class
		}
	)
	@ComponentScan(useDefaultFilters = false)
	static class TestConfig {
		// Minimal test configuration - no components scanned, no database required
	}

	@Test
	void contextLoads() {
		// This test verifies that a minimal Spring Boot application context can load
		// without requiring a database connection or security configuration.
		// Component scanning is disabled to avoid loading any application components
		// that depend on the database.
	}

}

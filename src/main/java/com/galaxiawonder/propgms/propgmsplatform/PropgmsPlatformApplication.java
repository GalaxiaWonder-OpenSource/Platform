
package com.galaxiawonder.propgms.propgmsplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * CatchUpPlatformApplication
 *
 * @summary
 * The main class of the CatchUp Platform application.
 * It is responsible for starting the Spring Boot application.
 * It also enables JPA auditing.
 *
 * @since 1.0
 */
@EnableJpaAuditing
@SpringBootApplication
public class PropgmsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropgmsPlatformApplication.class, args);
    }

}

package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurityConfiguration
 *
 * @summary
 * Configures the Spring Security filter chain for the application.
 * Allows unauthenticated access to Swagger endpoints and authentication routes only.
 * All other requests will be subject to default security behavior.
 *
 * This configuration disables CSRF and uses HTTP Basic authentication for simplicity.
 *
 * @since 1.0
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private static final String[] WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/auth/**"
    };

    /**
     * Configures the application's security filter chain.
     *
     * @param http the {@link HttpSecurity} configuration object provided by Spring
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during security setup
     *
     * @since 1.0
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
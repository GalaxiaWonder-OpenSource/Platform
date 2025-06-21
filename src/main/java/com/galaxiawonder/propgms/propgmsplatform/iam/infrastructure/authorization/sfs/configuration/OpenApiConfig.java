package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.authorization.sfs.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration for Swagger UI.
 *
 * <p>
 * This class configures the OpenAPI (Swagger) documentation for the Galaxia Wonder platform.
 * It enables interactive API documentation and defines a global Bearer Authentication scheme
 * for endpoints that require JWT-based authorization.
 * </p>
 *
 * <p>
 * Once configured, Swagger UI will display an "Authorize" button,
 * allowing users to input a valid Bearer token, which will then be
 * included in the `Authorization` header for all protected API requests.
 * </p>
 *
 * <p>
 * Example header: <pre>{@code Authorization: Bearer eyJhbGciOi...}</pre>
 * </p>
 *
 * <ul>
 *   <li><b>Security Scheme:</b> Adds a global Bearer token security scheme using JWT.</li>
 *   <li><b>Security Requirement:</b> Enforces this scheme across all endpoints (unless overridden).</li>
 *   <li><b>Info:</b> Sets title, version, and description of the API shown in Swagger UI.</li>
 * </ul>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Galaxia Wonder API")
                        .version("1.0")
                        .description("Documentación interactiva de la API del sistema"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)));
    }
}

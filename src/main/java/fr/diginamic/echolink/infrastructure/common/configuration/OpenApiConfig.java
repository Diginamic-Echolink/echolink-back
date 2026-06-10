package fr.diginamic.echolink.infrastructure.common.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;

/**
 * OpenAPI configuration for the EchoLink application.
 *
 * <p>
 * This configuration class customizes the generated Swagger/OpenAPI documentation
 * using SpringDoc.
 * </p>
 *
 * <h2>Main responsibilities</h2>
 * <ul>
 *     <li>Defines global API metadata (title, description, version, contact)</li>
 *     <li>Configures JWT Bearer authentication for secured endpoints</li>
 *     <li>Registers OpenAPI security schemes</li>
 *     <li>Excludes Spring Security-related types from Swagger models</li>
 * </ul>
 *
 * <h2>API context</h2>
 * <p>
 * The EchoLink API is an academic project composed of two main functional domains:
 * </p>
 *
 * <h3>🌦 Weather & Air Quality API</h3>
 * <ul>
 *     <li>Provides meteorological data and air quality indicators</li>
 *     <li>Supports multiple locations across France</li>
 *     <li>Used to monitor environmental conditions</li>
 * </ul>
 *
 * <h3>💬 Forum Module</h3>
 * <ul>
 *     <li>Section / Thread / Message management</li>
 *     <li>Enables structured discussions between users</li>
 * </ul>
 *
 * <h2>Security</h2>
 * <ul>
 *     <li>JWT Bearer authentication (OAuth2-style)</li>
 *     <li>Role-based access control (USER / ADMIN)</li>
 * </ul>
 *
 * <h2>Technical notes</h2>
 * <ul>
 *     <li>SpringDoc is used to generate OpenAPI documentation</li>
 *     <li>Authentication wrappers (Jwt, Authentication, Principal) are ignored in Swagger models</li>
 * </ul>
 *
 * <p>
 * This configuration is intended for an academic project and is not production-hardened.
 * </p>
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {

        SpringDocUtils.getConfig()
                .addRequestWrapperToIgnore(Authentication.class)
                .addRequestWrapperToIgnore(Principal.class)
                .addRequestWrapperToIgnore(Jwt.class);

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .schemaRequirement(SECURITY_SCHEME_NAME, bearerScheme());
    }

    private Info apiInfo() {
        return new Info()
                .title("EchoLink API")
                .description("""
                                EchoLink is an academic project providing two main domains:
                                
                                🌦 Weather & Air Quality API
                                - Provides meteorological data and air quality indicators
                                - Covers multiple locations across France
                                - Used to monitor environmental conditions
                                
                                💬 Forum Module
                                - Thread / Message / Section management
                                - Enables structured discussions between users
                                
                                🔐 Security
                                - OAuth2 authentication (JWT-based)
                                - Role-based access control (USER / ADMIN)
                                
                                📚 Context
                                - Academic project developed for learning purposes
                                """)
                .version("1.0.0")
                .contact(new Contact()
                        .name("EchoLink Team")
                        .email("contact@echolink.local"))
                .license(new License()
                        .name("Academic Use Only"));
    }

    private SecurityScheme bearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter your JWT token like: Bearer <token>");
    }
}

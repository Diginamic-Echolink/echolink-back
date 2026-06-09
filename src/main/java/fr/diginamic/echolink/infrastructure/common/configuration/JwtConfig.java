package fr.diginamic.echolink.infrastructure.common.configuration;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Configuration class responsible for JWT security components.
 * <p>
 * Provides the secret key, JWT encoder, and JWT decoder
 * used for token generation and validation.
 */
@Configuration
@EnableConfigurationProperties
public class JwtConfig {

    @Value("${app.security.jwt.secret}")
    private String secret;

    /**
     * Creates the secret key used to sign and verify JWT tokens.
     *
     * @return configured secret key
     */
    @Bean
    public SecretKey jwtSecretKey() {
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    /**
     * Creates the JWT encoder used to generate signed tokens.
     *
     * @param secretKey secret key used for token signing
     * @return configured JWT encoder
     */
    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    /**
     * Creates the JWT decoder used to validate and decode tokens.
     *
     * @param secretKey secret key used for token verification
     * @return configured JWT decoder
     */
    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

}

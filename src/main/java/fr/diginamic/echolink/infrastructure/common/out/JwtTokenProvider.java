package fr.diginamic.echolink.infrastructure.common.out;

import fr.diginamic.echolink.application.profile.port.out.TokenProvider;
import fr.diginamic.echolink.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * JWT-based implementation of the {@link TokenProvider} interface.
 * <p>
 * Generates signed JWT tokens containing user identity and roles.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtEncoder jwtEncoder;

    @Value("${spring.application.jwt.expiration}")
    private long expiration;

    /**
     * Generates a JWT token for the specified user.
     *
     * @param user authenticated user
     * @return generated JWT token
     */
    @Override
    public String generateToken(Profile user) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .subject(user.getId().toString())
                .claim("profileId", user.getId())
                .claim("email", user.getEmail())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims))
                .getTokenValue();
    }
}

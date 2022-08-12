package io.github.szokecsaba.vgdb.security;

import io.github.szokecsaba.vgdb.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenGenerator {
    private final JwtEncoder encoder;

    @Autowired
    public TokenGenerator(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generate(User user) {
        Instant now = Instant.now();
        long expiry = 36000L;
        String role = user.getRole();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getUsername())
                .claim("scope", role)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

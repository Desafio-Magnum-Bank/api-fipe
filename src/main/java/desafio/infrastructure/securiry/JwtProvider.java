package desafio.infrastructure.securiry;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Collections;

@ApplicationScoped
public class JwtProvider {

    public String generateToken(String username) {
        return Jwt.issuer("self")
                .subject(username)
                .claim("upn", username)
                .claim("groups", Collections.singletonList("USER"))
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
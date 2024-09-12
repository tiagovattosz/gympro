package br.edu.fema.gympro.security.service;

import br.edu.fema.gympro.security.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Map<String, Object> payload = new HashMap<>();
            payload.put("role", user.getRole().getValue());
            payload.put("idUser", user.getId());
            return JWT.create()
                    .withIssuer("auth-api")  // nome da aplicação que gerou o token
                    .withSubject(user.getUsername())  // gera o token com informação (username)
                    .withPayload(payload)
                    .withExpiresAt(this.generateExpirationDate())  // tempo de expiração do token
                    .sign(algorithm);  //criptografa usando algorithm
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token.");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)  // algoritmo de descriptografia
                    .withIssuer("auth-api") // token deve ter sido gerado pelo issuer
                    .build()
                    .verify(token) // descriptografa
                    .getSubject(); // coleta informações, no caso o username

        } catch (JWTVerificationException e) { //token nao verdadeiro ou expirado
            return ""; // retorna usuario vazio
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}


package tgb.cryptoexchange.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tgb.cryptoexchange.exception.EmptyResponseBodyException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class AuthLoginService {

    private final WebClient webClient;

    private final String username;

    private final String password;

    private Token token = null;

    public AuthLoginService(@Value("${tgb.service.auth.url:null}") String authUrl,
                            @Value("${tgb.service.auth.username:null}") String username,
                            @Value("${tgb.service.auth.password:null}") String password) {
        this.webClient = WebClient.builder().baseUrl(authUrl).build();
        this.username = username;
        this.password = password;
    }

    public String login() {
        if (token == null) {
            token = getToken();
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (token.expires.isBefore(now.minusMinutes(5))) {
                token = getToken();
            }
        }
        return token.tokenString;
    }

    private Token getToken() {
        String jwtTokenString = Optional.ofNullable(webClient.post()
                        .uri("/login")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .bodyValue(new LoginCredentials(username, password))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                        .block())
                .orElseThrow(EmptyResponseBodyException::new)
                .getData();
        DecodedJWT decodedJwt = JWT.decode(jwtTokenString);
        return new Token(
                jwtTokenString, decodedJwt.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    public record LoginCredentials(String username, String password) {}

    public record Token(String tokenString, LocalDateTime expires) {}
}

package br.uni.ms.libtec.authtec.service;

import br.uni.ms.libtec.authtec.client.UserTecClient;
import br.uni.ms.libtec.authtec.dto.LoginDTO;
import br.uni.ms.libtec.authtec.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserTecClient userClient;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String authenticate(LoginDTO dto) throws Exception {
        try {
            ResponseEntity<UserDto> response = userClient.validateUser(dto);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return generateToken(response.getBody());
            } else {
                throw new Exception("Credenciais inválidas");
            }
        } catch (Exception e) {
            throw new Exception("Falha na autenticação: " + e.getMessage());
        }
    }

    private String generateToken(UserDto user) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("nome", user.getNome());
        claims.put("papel", user.getPapel());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

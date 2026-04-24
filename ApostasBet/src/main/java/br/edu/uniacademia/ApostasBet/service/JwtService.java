package br.edu.uniacademia.ApostasBet.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final long EXPIRATION_TIME = 1000*60*60;
    private final String secretKey = "SFsdfsdf34234Hersiudiduw9qe8qewuiuewiru389ruwieruewere4534543EWRWRwe";

    public String gerarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("nome", userDetails.getUsername());
        claims.put("matricula", "1234567");
        claims.put("email", "teste@teste");
        claims.put("id", "58");

        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("role", role);

        return criarToken(claims, userDetails.getUsername());
    }

    private String criarToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodasAsClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodasAsClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public Boolean tokenValido(String token, String username) {
        final String extraidoUsername = extrairUsername(token);
        return (extraidoUsername.equals(username) && !tokenExpirado(token));
    }

    public String extrairUsername(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public Date extrairExpiracao(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }

    private Boolean tokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

}

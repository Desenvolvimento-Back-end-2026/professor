package br.edu.uniacademia.ApostasBet.service;


import br.edu.uniacademia.ApostasBet.security.UserLogado;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final long EXPIRATION_TIME = 1000*60*60;
    //private final String secretKey = "SFsdfsdf34234Hersiudiduw9qe8qewuiuewiru389ruwieruewere4534543EWRWRwe";
    @Value("${jwt.secret}")
    private String secretKey;


    public String gerarToken(UserDetails userDetails) {
        System.out.println("KEY :: "+secretKey);
        Map<String, Object> claims = new HashMap<>();

        claims.put("nome", ((UserLogado)userDetails).getUser().getNome());
        //claims.put("matricula", );
        claims.put("email", ((UserLogado)userDetails).getUser().getEmail());
        claims.put("id", ((UserLogado)userDetails).getUser().getId());

        List<String> role = userDetails.getAuthorities()
                .stream().map(r->r.getAuthority().toString()).toList();

//        String role = userDetails.getAuthorities().iterator().next().getAuthority();
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

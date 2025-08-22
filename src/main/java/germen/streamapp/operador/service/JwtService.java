package germen.streamapp.operador.service;

import germen.streamapp.operador.enums.TokenType;
import germen.streamapp.operador.model.JwtToken;
import germen.streamapp.operador.model.User;
import germen.streamapp.operador.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    // Generar Access Token
    public String generateToken(User user){
        return buildToken(user, jwtExpiration);
    }

    // Generar Refresh Token
    public String generateRefreshToken(User user){
        return buildToken(user, refreshExpiration);
    }

    // Construir JWT
    private String buildToken(User user, Long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .subject(user.getEmail())
                .claims(Map.of("firstName", user.getFirstName(), "lastName", user.getLastName(), "id", user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extraer email del token
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    // Validar token
    public boolean isTokenValid(String token, String email){
        final String username = extractUsername(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    // Revisar expiración
    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtener token de la BD
    public JwtToken getToken(Long id){
        return jwtTokenRepository.findById(id).orElseThrow();
    }

    public JwtToken updateUserToken(Long userId, String token) {
        Optional<JwtToken> optionalToken = jwtTokenRepository.findByUser_Id(userId);

        JwtToken jwtToken = optionalToken.orElseGet(() -> {
            JwtToken newToken = new JwtToken();
            newToken.setUser(new User(userId)); // solo necesitas el id
            newToken.setTokenType(TokenType.BEARER);
            newToken.setRevoked(false);
            newToken.setExpired(false);
            return newToken;
        });



        jwtToken.setToken(token);
        jwtToken.setRevoked(false);
        jwtToken.setExpired(false);

        return jwtTokenRepository.save(jwtToken);
    }
}

package net.weg.crud;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {


    private SecretKey key;


    public JwtUtil(){
        PasswordEncoder encoder =  new BCryptPasswordEncoder();
        String senha =  encoder.encode("senha123");
        this.key = Keys.hmacShaKeyFor(senha.getBytes());
    }

    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .issuer("WEG EQUIPAMENTOS ELÉTRICOS S/A")
                .issuedAt(new Date())
                .expiration(new Date( new Date().getTime()+300000))
                .signWith(this.key, Jwts.SIG.HS256)
                .subject(userDetails.getUsername())
                .compact();
    }


    private JwtParser getParser(){
        return Jwts.parser().verifyWith(this.key).build();
    }
    private Jws<Claims> validarToken(String token){
        var parser = getParser();
        return parser.parseSignedClaims(token);
    }

    public String getUsername(String token){
        return validarToken(token).getPayload().getSubject();
    }

}

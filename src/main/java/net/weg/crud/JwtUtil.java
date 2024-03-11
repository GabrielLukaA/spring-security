package net.weg.crud;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {


    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .issuer("WEG EQUIPAMENTOS ELÉTRICOS S/A")
                .issuedAt(new Date())
                .expiration(new Date( new Date().getTime()+300000))
                .signWith(SignatureAlgorithm.NONE, "senha123")
                .subject(userDetails.getUsername())
                .compact();
    }


    private JwtParser getParser(){
        return Jwts.parser().setSigningKey("senha123").build();
    }
    private Jws<Claims> validarToken(String token){
        var parser = getParser();
        return parser.parseSignedClaims(token);
    }

    public String getUsername(String token){
        return validarToken(token).getPayload().getSubject();
    }

}

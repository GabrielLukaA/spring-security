package net.weg.crud;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public void validarToken(String token){
        var parser = getParser();
        parser.parseSignedClaims(token);
    }

    public String getUsername(String token){
        return getParser().parseSignedClaims(token).getPayload().getSubject();
    }

}

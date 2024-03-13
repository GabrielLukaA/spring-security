package net.weg.crud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    public String gerarToken(UserDetails userDetails){
        Algorithm algorithm = Algorithm.HMAC256("senha123");
        return JWT.create()
                .withIssuer("WEG EQUIPAMENTOS ELÉTRICOS S/A")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date( new Date().getTime()+300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getUsername(String token){
        return JWT.decode(token).getSubject();
    }

}

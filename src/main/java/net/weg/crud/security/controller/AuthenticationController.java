package net.weg.crud.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.crud.model.UserLogin;
import net.weg.crud.security.utils.CookieUtil;
import net.weg.crud.security.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil = new JwtUtil();
    private final CookieUtil cookieUtil = new CookieUtil();


    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin usuario, HttpServletRequest request, HttpServletResponse response){



        try {


            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // cria contexto
            // sem o contexto de segurança é impossível ter consistência na autenticação, exigindo a cada requisição
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//            // setta um obj de authenticação, com o objeto  já autenticado!
//            context.setAuthentication(authentication);
//            securityContextRepository.saveContext(context, request, response);
            UserDetails user =  (UserDetails) authentication.getPrincipal();
            Cookie cookie = cookieUtil.gerarCookieJwt(user);
            response.addCookie(cookie);
//            SecurityContextHolder.setContext(context);

            return ResponseEntity.ok("Autenticação bem-sucedida");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na authenticação");
        }

    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response){

        try {
            Cookie cookie = cookieUtil.getCookie(request, "JWT");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        } catch (Exception e){
            response.setStatus(401);
        }

    }
}

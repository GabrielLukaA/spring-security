package net.weg.crud;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.crud.model.UserLogin;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;



    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin usuario, HttpServletRequest request, HttpServletResponse response){



        try {


            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // cria contexto
            // sem o contexto de segurança é impossível ter consistência na autenticação, exigindo a cada requisição
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            // setta um obj de authenticação, com o objeto  já autenticado!
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

//            SecurityContextHolder.setContext(context);

            return ResponseEntity.ok("Autenticação bem-sucedida");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na authenticação");
        }

    }
}
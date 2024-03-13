package net.weg.crud;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class FilterAuthentication extends OncePerRequestFilter {


    private final CookieUtil cookieUtil = new CookieUtil();
    private SecurityContextRepository securityContextRepository;
    private final JwtUtil jwtUtil = new JwtUtil();
    private AuthenticationService userDetailsService;

    //executará a cada request dentro da API
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Para capturar o cookie do navegador do usuário é necessário
        if (!publicRoute(request)) {
            // Busca e validação do token
            Cookie cookie;
            try {
                 cookie = cookieUtil.getCookie(request, "JWT");

            } catch (Exception e){
                response.setStatus(401);
                return;
            }
            // Valida o JWT
            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);

            // Criação do usuário autenticado
            UserDetails user = userDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            // Salvamento do usuário autenticado no Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // setta um obj de authenticação, com o objeto  já autenticado!
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

            // Renovação do JWT e Cookie
            Cookie cookieRenovado = cookieUtil.gerarCookieJwt(user);
            response.addCookie(cookieRenovado);

        }
        // Continuação da requisição
        filterChain.doFilter(request, response);

    }

    private boolean publicRoute(HttpServletRequest request) {
        System.out.println(request.toString());
        return request.getRequestURI().equals("/login") && request.getMethod().equals("POST");
    }


}

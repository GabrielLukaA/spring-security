package net.weg.crud;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterAuthentication extends OncePerRequestFilter {


    private CookieUtil cookieUtil = new CookieUtil();
    private JwtUtil jwtUtil = new JwtUtil();

    //executará a cada request dentro da API
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Para capturar o cookie do navegador do usuário é necessário
        Cookie cookie = cookieUtil.getCookie(request, "JWT");
        String token = cookie.getValue();
        jwtUtil.validarToken(token);
        filterChain.doFilter(request, response);

    }


}

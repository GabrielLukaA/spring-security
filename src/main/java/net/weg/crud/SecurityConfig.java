package net.weg.crud;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.crud.model.User;
import net.weg.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {


    private final SecurityContextRepository repo;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Garante que não terá uma interceptação na requisição, verifica se a solicitação veio do lugar certo????
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                // Isso é sequencial ein mané, não esquece
                authorizeRequests
                        .requestMatchers( HttpMethod.GET,"/user").hasAuthority("GET")
//                        .requestMatchers( HttpMethod.POST,"/user").hasAuthority("POST")
//                        .requestMatchers("/user").permitAll()
                        .anyRequest().authenticated()
                );

        // Para que o usuário se mantenha autenticado, mantendo a sessão da fera
        // Apenas para manter o contexto do usuário
        httpSecurity.securityContext(((context) -> context.securityContextRepository(repo)));



        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.logout(Customizer.withDefaults());
        return httpSecurity.build();
    }

    //    @Bean
//    public InMemoryUserDetailsManager inMemoryUser(){
//         UserDetails user  = User.withDefaultPasswordEncoder().username("mi72").password("M!7dois").build();
//    return new InMemoryUserDetailsManager(user);
//    }



}

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
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers( HttpMethod.GET,"/user").hasAuthority("GET")
//                        .requestMatchers( HttpMethod.POST,"/user").hasAuthority("POST")
//                        .requestMatchers("/user").permitAll()
                        .anyRequest().authenticated()
                );

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

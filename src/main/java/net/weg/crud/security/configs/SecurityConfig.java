package net.weg.crud.security.configs;

import lombok.AllArgsConstructor;
import net.weg.crud.security.filter.FilterAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {


    private final SecurityContextRepository repo;
    private final FilterAuthentication filterAuthentication;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Garante que não terá uma interceptação na requisição, verifica se a solicitação veio do lugar certo????
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                // Isso é sequencial ein mané, não esquece
                authorizeRequests
                        .requestMatchers( HttpMethod.GET,"/user/**").hasAuthority("GET")
                        .requestMatchers( HttpMethod.POST,"/user").hasAuthority("POSTUSER")
                        .requestMatchers( HttpMethod.POST,"/login").permitAll()
//                        .requestMatchers( HttpMethod.POST,"/user").hasAuthority("POST")
//                        .requestMatchers("/user").permitAll()
                        .anyRequest().authenticated()
                );

        // Para que o usuário se mantenha autenticado, mantendo a sessão da fera
        // Apenas para manter o contexto do usuário
         httpSecurity.securityContext(((context) -> context.securityContextRepository(repo)));


//
//        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.logout(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        httpSecurity.addFilterBefore(filterAuthentication, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }



    //    @Bean
//    public InMemoryUserDetailsManager inMemoryUser(){
//         UserDetails user  = User.withDefaultPasswordEncoder().username("mi72").password("M!7dois").build();
//    return new InMemoryUserDetailsManager(user);
//    }



}

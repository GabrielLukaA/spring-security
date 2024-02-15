package net.weg.crud;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.crud.model.User;
import net.weg.crud.repository.UserRepository;
import org.modelmapper.internal.bytebuddy.build.Plugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class ApiConfig {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setName("Teste");
        user.setUsuarioDetailsEntity(UsuarioDetailsEntity.builder()
                .user(user).enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username("teste")
                .password("teste123")
                .build());

        userRepository.save(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFiilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.userDetailsService(userDetailsService());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return authenticationService;
    }


//    @Bean
//    public InMemoryUserDetailsManager inMemoryUser(){
//         UserDetails user  = User.withDefaultPasswordEncoder().username("mi72").password("M!7dois").build();
//    return new InMemoryUserDetailsManager(user);
//    }

}

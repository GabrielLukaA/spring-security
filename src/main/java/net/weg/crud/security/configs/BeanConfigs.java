package net.weg.crud.security.configs;

import lombok.AllArgsConstructor;
import net.weg.crud.security.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@AllArgsConstructor
@Configuration
public class BeanConfigs {

    private final AuthenticationService authenticationService;

    @Bean
    public CorsConfigurationSource corsConfig(){
        CorsConfiguration  corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("POST",""));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return corsConfigurationSource;
    }

//    @Autowired
//    public void config(AuthenticationManagerBuilder auth, AuthenticationService authenticationService) throws Exception {
//        auth.userDetailsService(authenticationService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return  new BCryptPasswordEncoder();
//    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider dao  = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(new BCryptPasswordEncoder());
        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao);

    }



//        @Bean
//    public UserDetailsService userDetailsService() {
//        return authenticationService;
//    }



}

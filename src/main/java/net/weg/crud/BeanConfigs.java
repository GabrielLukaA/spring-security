package net.weg.crud;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@AllArgsConstructor
@Configuration
public class BeanConfigs {

    private final AuthenticationService authenticationService;


//    @Autowired
//    public void config(AuthenticationManagerBuilder auth, AuthenticationService authenticationService) throws Exception {
//        auth.userDetailsService(authenticationService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider dao  = new DaoAuthenticationProvider();
//        dao.setPasswordEncoder(new BCryptPasswordEncoder());
        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao);

    }



//        @Bean
//    public UserDetailsService userDetailsService() {
//        return authenticationService;
//    }



}

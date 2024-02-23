package net.weg.crud;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.crud.model.User;
import net.weg.crud.repository.UserRepository;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        Collection<Authorization> authorizations = new ArrayList<>();
        authorizations.add(Authorization.GET);
        user.setName("Teste");
        user.setUsuarioDetailsEntity(UsuarioDetailsEntity.builder()
                .user(user).enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username("teste")
                .password("teste123")
                        .authorities(List.of(Authorization.GET, Authorization.POST))
                .build());

        userRepository.save(user);
    }

}

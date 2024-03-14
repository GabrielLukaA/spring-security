package net.weg.crud.security.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import net.weg.crud.model.User;
import net.weg.crud.security.model.enums.Authorization;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Builder
public class UsuarioDetailsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    // caso não use os atributos boleanos é necessário criar um método retornando 'true'
    // importante lembrar que, graças ao lindo spring os métodos já são gerados automáticamentes, mas eles estão presentes na classe devido ao @Data
    // No caso de poucos atributos não se tem motivo para ter uma classe secundária
    //Caso precise criar, dê uma olhada noa classe UsuarioDetails.Java, essa classe não persiste dados!!!
    private boolean enabled = true;
    private boolean accountNonLocked= true;
    private boolean accountNonExpired= true;
    private boolean credentialsNonExpired= true;
    private Collection<Authorization> authorities = null;
    @OneToOne(mappedBy = "usuarioDetailsEntity")
    @JsonIgnore
    private User user;

}

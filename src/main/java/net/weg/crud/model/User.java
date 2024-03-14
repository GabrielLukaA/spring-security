package net.weg.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.crud.security.model.entity.UsuarioDetailsEntity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
//    private String password;
    private Boolean isActive;
    @OneToOne(cascade = CascadeType.ALL)
    private Archive picture;
    private Boolean isEnabled;
    @OneToOne(cascade = CascadeType.ALL)
    private UsuarioDetailsEntity usuarioDetailsEntity;
}

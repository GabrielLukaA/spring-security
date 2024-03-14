package net.weg.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.crud.security.model.entity.UsuarioDetailsEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {
    private String name;
    private String email;
    private Integer age;
    private String password;
    private Boolean isActive;
    private UsuarioDetailsEntity usuarioDetailsEntity;
}

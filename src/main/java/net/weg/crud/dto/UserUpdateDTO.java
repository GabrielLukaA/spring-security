package net.weg.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String password;
    private Boolean isActive;
}

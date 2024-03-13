package net.weg.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@AllArgsConstructor
public enum Authorization implements GrantedAuthority {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),

    POSTUSER("POSTUSER");


private final String name;
    @Override
    public String getAuthority() {
        return name();
    }
}

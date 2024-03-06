//package net.weg.crud;
//
//import lombok.AllArgsConstructor;
//import net.weg.crud.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//      Essa classe serve apenas para fins estéticos, tendo em vista que não queremos usar todos esses métodos na classe User
//@AllArgsConstructor
//public class UsuarioDetails implements UserDetails {
//
//    Normalmente se usa o @allargs para passar esse usuário, pois é necessário uma referência
//    private final User usuario;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return usuario.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return usuario.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}

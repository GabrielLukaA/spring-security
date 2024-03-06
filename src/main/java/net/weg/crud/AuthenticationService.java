package net.weg.crud;

import lombok.AllArgsConstructor;
import net.weg.crud.model.User;
import net.weg.crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {


    private UserRepository userRepository;

    // Para o usuário poder ser tanto via email ou CPF, você faz uso de um findBy, caso não seja encontrado você realiza um outro findBy, assim encontrando
    // usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser =  userRepository.findUserByUsuarioDetailsEntity_Username(username);
        if(optionalUser.isPresent()){
            return optionalUser.get().getUsuarioDetailsEntity();
        }

        throw new UsernameNotFoundException("Dados inválidos!");
    }
}

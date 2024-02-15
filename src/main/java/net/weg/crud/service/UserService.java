package net.weg.crud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.crud.dto.UserCreateDTO;
import net.weg.crud.dto.UserUpdateDTO;
import net.weg.crud.model.Archive;
import net.weg.crud.model.User;
import net.weg.crud.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    public User find(Long id) {
        return userRepository.findById(id).get();
    }

    public Collection<User> find() {
        return userRepository.findAll();
    }

    public User updatePassword(Long id, String password) {
        User user = userRepository.findById(id).get();
//        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public User updateArchive(Long id, MultipartFile archive) {
        User user = userRepository.findById(id).get();
        try {
            Archive a = new Archive();
            a.setData(archive.getBytes());
            a.setName(archive.getOriginalFilename());
            a.setType(archive.getContentType());
            user.setPicture(a);
        } catch (IOException ignore) {

        }

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User create(UserCreateDTO userDTO) {
        System.out.println(userDTO);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    public User update(UserUpdateDTO userDTO) {

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }


    public User save(String userJSON, MultipartFile archive) {
        User user = new User();
        try {
            user = objectMapper.readValue(userJSON , User.class);
            Archive a = new Archive();
            a.setData(archive.getBytes());
            a.setName(archive.getOriginalFilename());
            a.setType(archive.getContentType());
            user.setPicture(a);
        } catch (Exception ignore){

        }

        return userRepository.save(user);
    }




    public User updateAccountStatus(Long id, Boolean isActive) {
        User user = userRepository.findById(id).get();
        user.setIsActive(isActive);
        userRepository.save(user);
        return user;
    }
}

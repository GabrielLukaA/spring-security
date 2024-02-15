package net.weg.crud.controller;

import lombok.AllArgsConstructor;
import net.weg.crud.dto.UserCreateDTO;
import net.weg.crud.dto.UserUpdateDTO;
import net.weg.crud.model.User;
import net.weg.crud.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/archive")
    public User createWithArchive(@RequestParam String user, @RequestParam MultipartFile archive) {
        return userService.save(user, archive);
    }

    @PutMapping("/archive")
    public User updateWithArchive(@RequestParam String user, @RequestParam MultipartFile archive) {
        return userService.save(user, archive);
    }


    @PostMapping
    public User create(@RequestBody UserCreateDTO userDTO) {
        System.out.println(userDTO);
        return userService.create(userDTO);
    }


    @PutMapping
    public User update( @RequestBody UserUpdateDTO userDTO) {
        return userService.update(userDTO);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public User find(@PathVariable Long id) {
        return userService.find(id);
    }

    @GetMapping
    public Collection<User> find() {
        return userService.find();
    }

    @PatchMapping("/password/{id}")
    public User updatePassword(@PathVariable Long id, @RequestBody String password) {
        return userService.updatePassword(id, password);
    }

    @PatchMapping("/status/{id}")
    public User updateAccountStatus(@PathVariable Long id, @RequestBody Boolean isActive) {
        return userService.updateAccountStatus(id, isActive);
    }

    @PatchMapping("picture/{id}")
    public User updateArchive(@PathVariable Long id, @RequestParam MultipartFile archive) {
        return userService.updateArchive(id, archive);
    }


}

package com.vicras.abaclib.use.controller;

import com.vicras.abaclib.use.dto.JWTokenDTO;
import com.vicras.abaclib.use.dto.LoginDTO;
import com.vicras.abaclib.use.dto.NewUserDTO;
import com.vicras.abaclib.use.dto.UserDTO;
import com.vicras.abaclib.use.service.AuthService;
import com.vicras.abaclib.use.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping()
    public UserDTO addUser(@RequestBody NewUserDTO newUserDTO) {
        return authService.register(newUserDTO);
    }

    @PostMapping("/login")
    public JWTokenDTO login(@RequestBody LoginDTO newUserDTO) {
        return authService.login(newUserDTO);
    }

    @DeleteMapping("/{id}")
    public UserDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
}

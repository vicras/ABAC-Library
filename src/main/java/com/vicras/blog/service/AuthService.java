package com.vicras.blog.service;

import com.vicras.blog.dto.JWTokenDTO;
import com.vicras.blog.dto.LoginDTO;
import com.vicras.blog.dto.NewUserDTO;
import com.vicras.blog.dto.UserDTO;

public interface AuthService {
    UserDTO register(NewUserDTO userDTO);

    JWTokenDTO login(LoginDTO cred);
}

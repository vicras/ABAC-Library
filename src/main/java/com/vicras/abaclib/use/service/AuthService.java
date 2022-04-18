package com.vicras.abaclib.use.service;

import com.vicras.abaclib.use.dto.JWTokenDTO;
import com.vicras.abaclib.use.dto.LoginDTO;
import com.vicras.abaclib.use.dto.NewUserDTO;
import com.vicras.abaclib.use.dto.UserDTO;

public interface AuthService {
    UserDTO register(NewUserDTO userDTO);

    JWTokenDTO login(LoginDTO cred);
}

package com.vicras.abaclib.use.service;

import com.vicras.abaclib.use.dto.UserDTO;
import com.vicras.abaclib.use.model.CommonUser;

import java.util.List;

public interface UserService {
    CommonUser findUserByLogin(String login);

    CommonUser findUserById(Long userId);

    UserDTO deleteUserById(Long id);

    List<UserDTO> getAllUsers();
}

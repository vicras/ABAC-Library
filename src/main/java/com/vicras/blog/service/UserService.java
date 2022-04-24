package com.vicras.blog.service;

import com.vicras.blog.dto.UserDTO;
import com.vicras.blog.model.CommonUser;

import java.util.List;

public interface UserService {
    CommonUser findUserByLogin(String login);

    CommonUser findUserById(Long userId);

    UserDTO deleteUserById(Long id);

    List<UserDTO> getAllUsers();
}

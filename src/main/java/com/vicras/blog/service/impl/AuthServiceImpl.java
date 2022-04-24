package com.vicras.blog.service.impl;

import static com.vicras.blog.model.Action.ADD_USERS;

import com.vicras.blog.abac.aspect.annotation.AbacSecure;
import com.vicras.blog.dto.JWTokenDTO;
import com.vicras.blog.dto.LoginDTO;
import com.vicras.blog.dto.NewUserDTO;
import com.vicras.blog.dto.UserDTO;
import com.vicras.blog.exception.exceptions.business.UserExistException;
import com.vicras.blog.exception.exceptions.security.AuthenticationException;
import com.vicras.blog.mapper.UserMapper;
import com.vicras.blog.model.CommonUser;
import com.vicras.blog.repository.CommonUserRepository;
import com.vicras.blog.security.service.JWTService;
import com.vicras.blog.service.AuthService;
import com.vicras.blog.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final CommonUserRepository repository;
    private final UserService userService;
    private final JWTService jwtService;

    @Override
    @AbacSecure(ADD_USERS)
    public UserDTO register(NewUserDTO userDTO) {
        validateUserExist(userDTO.getLogin());
        var user = getNewUser(userDTO);
        repository.save(user);
        return mapper.toResponse(user);
    }

    private void validateUserExist(String login) {
        repository.findByLogin(login).ifPresent((ignore) -> {
                    throw new UserExistException("User already exist");
                }
        );
    }

    private CommonUser getNewUser(NewUserDTO signInDto) {
        var user = new CommonUser();
        user.setLogin(signInDto.getLogin());
        user.setPassword(encoder.encode(signInDto.getPassword()));
        user.setPosition(signInDto.getPosition());
        user.setDepartment(signInDto.getDepartment());
        return user;
    }

    @Override
    public JWTokenDTO login(LoginDTO cred) {
        CommonUser user = userService.findUserByLogin(cred.getLogin());
        if (encoder.matches(cred.getPassword(), user.getPassword())) {
            return new JWTokenDTO(jwtService.getToken(user), mapper.toResponse(user));
        }
        throw new AuthenticationException("Incorrect password or username");
    }
}
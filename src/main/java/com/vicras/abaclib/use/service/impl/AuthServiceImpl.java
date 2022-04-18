package com.vicras.abaclib.use.service.impl;

import com.vicras.abaclib.use.dto.JWTokenDTO;
import com.vicras.abaclib.use.dto.LoginDTO;
import com.vicras.abaclib.use.dto.NewUserDTO;
import com.vicras.abaclib.use.dto.UserDTO;
import com.vicras.abaclib.use.exception.exceptions.business.UserExistException;
import com.vicras.abaclib.use.exception.exceptions.security.AuthenticationException;
import com.vicras.abaclib.use.mapper.UserMapper;
import com.vicras.abaclib.use.model.CommonUser;
import com.vicras.abaclib.use.repository.CommonUserRepository;
import com.vicras.abaclib.use.security.service.JWTService;
import com.vicras.abaclib.use.service.AuthService;
import com.vicras.abaclib.use.service.UserService;
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
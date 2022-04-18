package com.vicras.abaclib.use.service.impl;

import static com.vicras.abaclib.use.model.Action.DELETE_USERS;
import static com.vicras.abaclib.use.model.Action.VIEW_USERS;

import com.vicras.abaclib.use.abac.aspect.annotation.AbacSecure;
import com.vicras.abaclib.use.dto.UserDTO;
import com.vicras.abaclib.use.exception.exceptions.business.EntityNotFoundException;
import com.vicras.abaclib.use.mapper.UserMapper;
import com.vicras.abaclib.use.model.CommonUser;
import com.vicras.abaclib.use.repository.CommonUserRepository;
import com.vicras.abaclib.use.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final CommonUserRepository repository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean accountNotExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNotLocked = true;
        boolean isUserActive = true;

        var user = findUserByLogin(email);

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                isUserActive,
                accountNotExpired,
                credentialsNotExpired,
                accountNotLocked,
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }

    @Override
    public CommonUser findUserByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(CommonUser.class, login));
    }

    @Override
    public CommonUser findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CommonUser.class, id.toString()));
    }

    @Override
    @Transactional
    @AbacSecure(DELETE_USERS)
    public UserDTO deleteUserById(Long id) {
        var user = findUserById(id);
        repository.deleteById(id);
        return userMapper.toResponse(user);
    }

    @Override
    @AbacSecure(VIEW_USERS)
    public List<UserDTO> getAllUsers() {
        return userMapper.toResponse(repository.findAll());
    }
}
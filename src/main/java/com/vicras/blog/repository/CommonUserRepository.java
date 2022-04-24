package com.vicras.blog.repository;

import com.vicras.blog.model.CommonUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {
    Optional<CommonUser> findByLogin(String login);
}

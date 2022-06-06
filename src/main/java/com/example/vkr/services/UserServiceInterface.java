package com.example.vkr.services;

import com.example.vkr.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    User findByLogin(String login);

    User deleteByLogin(String login);

    List<User> findAll();

    void deleteById(Long id);

    void save(User user);

    Optional<User> findById(Long id);
}

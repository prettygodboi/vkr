package com.example.vkr.services;

import com.example.vkr.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByLogin(String login);

    void deleteByLogin(String login);

    List<User> findAll();

    void deleteById(Long id);

    void save(User user);

    User findById(Long id);
}

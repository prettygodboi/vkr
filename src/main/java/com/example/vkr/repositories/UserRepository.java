package com.example.vkr.repositories;

import com.example.vkr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLogin(String login);

    void deleteByLogin(String login);
}

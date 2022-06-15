package com.example.vkr.repositories;

import com.example.vkr.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Login(String login);

    List<Order> findAllByProduct_User_Login(String login);
}

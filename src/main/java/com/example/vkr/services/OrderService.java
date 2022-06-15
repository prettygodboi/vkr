package com.example.vkr.services;

import com.example.vkr.entities.Order;
import com.example.vkr.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void save(Order order){
        orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public List<Order> findOrderByLogin(String login){
        return orderRepository.findAllByUser_Login(login);
    }

    public List<Order> findOrderByProductUserLogin(String login){
        return orderRepository.findAllByProduct_User_Login(login);
    }
}

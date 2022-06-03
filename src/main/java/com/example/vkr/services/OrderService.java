package com.example.vkr.services;

import com.example.vkr.entities.Order;
import com.example.vkr.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }
    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }
}

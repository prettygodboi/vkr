package com.example.vkr.controllers;

import com.example.vkr.entities.Product;
import com.example.vkr.services.OrderService;
import com.example.vkr.services.ProductService;
import com.example.vkr.services.RoleService;
import com.example.vkr.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserServiceImpl userServiceImpl;
    private final RoleService roleService;

    @GetMapping("/addProduct")
    public void createProduct(){
        Product product = Product.builder().name("Шлёпка").build();
        productService.save(product);
    }
    @GetMapping("/createUser")
    public void createUser(){

    }
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id){
        productService.deleteById(id);
        return "";
    }
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id){
        orderService.deleteById(id);
        return "";
    }

}

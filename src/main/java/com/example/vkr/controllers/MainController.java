package com.example.vkr.controllers;

import com.example.vkr.entities.Product;
import com.example.vkr.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;

    @GetMapping("/addProduct")
    public void createProduct(){
        Product product = Product.builder().name("Шлёпка").build();
        productService.save(product);
    }
    @GetMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable(value = "id") Long id){
        productService.deleteById(id);
    }

}

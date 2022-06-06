package com.example.vkr.services;

import com.example.vkr.entities.Product;
import com.example.vkr.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void save(Product product){
        productRepository.save(product);
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}

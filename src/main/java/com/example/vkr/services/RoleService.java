package com.example.vkr.services;

import com.example.vkr.entities.Role;
import com.example.vkr.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.getById(id);
    }
}

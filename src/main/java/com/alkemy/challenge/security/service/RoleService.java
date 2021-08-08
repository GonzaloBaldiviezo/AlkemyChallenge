package com.alkemy.challenge.security.service;

import com.alkemy.challenge.security.entity.Role;
import com.alkemy.challenge.security.enums.ERole;
import com.alkemy.challenge.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByRole(ERole role) {
        return roleRepository.findByRole(role);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}

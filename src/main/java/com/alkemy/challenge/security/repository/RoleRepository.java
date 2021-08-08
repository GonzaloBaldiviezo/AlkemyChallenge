package com.alkemy.challenge.security.repository;

import com.alkemy.challenge.security.entity.Role;
import com.alkemy.challenge.security.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(ERole role);
}

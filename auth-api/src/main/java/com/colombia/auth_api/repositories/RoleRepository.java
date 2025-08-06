package com.colombia.auth_api.repositories;

import com.colombia.auth_api.models.Role;
import com.colombia.auth_api.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
package com.colombia.auth_api.service;

import com.colombia.auth_api.models.Role;
import com.colombia.auth_api.models.RoleName;
import com.colombia.auth_api.models.User;
import com.colombia.auth_api.repositories.RoleRepository;
import com.colombia.auth_api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class DataInitializerService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void initialize() {
        // Asegurar que los roles existan en la base de datos
        if (roleRepository.findByName(RoleName.USER).isEmpty()) {
            roleRepository.save(new Role(RoleName.USER));
        }
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            roleRepository.save(new Role(RoleName.ADMIN));
        }

        // Ahora que los roles están garantizados, los volvemos a buscar
        Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow();
        Role adminRole = roleRepository.findByName(RoleName.ADMIN).orElseThrow();

        // Crear un usuario administrador si no existe
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("Super Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(Set.of(adminRole));
            userRepository.save(adminUser);
            System.out.println("Usuario administrador creado con éxito.");
        }

        // Crear un usuario de prueba si no existe
        if (userRepository.findByEmail("user@example.com").isEmpty()) {
            User testUser = new User();
            testUser.setUsername("Test User");
            testUser.setEmail("user@example.com");
            testUser.setPassword(passwordEncoder.encode("password123"));
            testUser.setRoles(Set.of(userRole));
            userRepository.save(testUser);
            System.out.println("Usuario de prueba creado con éxito.");
        }
    }
}
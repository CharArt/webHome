package ru.specialist.java.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.specialist.java.spring.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
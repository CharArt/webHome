package ru.specialist.java.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.specialist.java.spring.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByUserId (long user_id);
}
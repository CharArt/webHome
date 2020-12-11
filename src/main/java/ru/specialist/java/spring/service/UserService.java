package ru.specialist.java.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.specialist.java.spring.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    void create(User user);
}
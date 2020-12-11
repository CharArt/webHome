package ru.specialist.java.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.specialist.java.spring.entity.User;
import ru.specialist.java.spring.repository.RoleRepository;
import ru.specialist.java.spring.repository.UserRepository;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder cryptPasswordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll(Sort.by("username"));
    }

    public void create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException();
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("USER")));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
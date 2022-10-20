package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    void save(User user);

    void deleteUser(Long id);

    void editUser(Long id, User user);
}

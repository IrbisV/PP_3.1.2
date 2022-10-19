package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserServiceImpl userService;

    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<User> getUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (userService.getUser(user.getId()) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
}
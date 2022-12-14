package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "main-page";
    }

    @GetMapping("/add-user")
    public String addUser(Model model) {
        User user = new User();
        user.setRoles(new HashSet<>(Arrays.asList(userService.getAllRoles().get(0))));
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("newUser", user);
        return "user-edit-page";
    }

    @GetMapping("/user-info/{id}")
    public String userInfo(@PathVariable("id") long id, Model model) {
        User currentUser = userService.getUser(id);
        model.addAttribute("userInfo", currentUser);
        return "user-page";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/";
    }

    @RequestMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @RequestMapping("/update-info/{id}")
    public String userInfo(Model model, @PathVariable("id") long id) {
        User currentUser = userService.getUser(id);
        Set<Role> roles = new HashSet<>();
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("newUser", currentUser);
        return "user-edit-page";
    }
}
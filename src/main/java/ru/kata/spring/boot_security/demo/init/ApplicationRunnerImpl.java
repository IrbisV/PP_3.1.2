package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final UserServiceImpl userService;
    public ApplicationRunnerImpl(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        userService.save(roleUser);
        userService.save(roleAdmin);
        User adminUser = new User("adminUser", "adminUser"
                , "Dmitriy", "Vanukov", 33
                , "adminUser@mail.ru", new HashSet<>(Arrays.asList(roleUser, roleAdmin))); //pass - adminUser
        userService.save(adminUser);

        User admin = new User("admin", "admin"
                , "admin", "admin", 1
                , "admin@mail.ru", new HashSet<>(Arrays.asList(roleAdmin))); //pass - admin
        userService.save(admin);

        User user = new User("user", "user"
                , "user", "user", 1
                ,"user@mail.ru", new HashSet<>(Arrays.asList(roleUser))); //pass - user
        userService.save(user);


    }
}

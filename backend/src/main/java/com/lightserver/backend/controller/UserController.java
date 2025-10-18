package com.lightserver.backend.controller;

import com.lightserver.backend.model.User;
import com.lightserver.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/add")
    public String addUser(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Użytkownik o tej nazwie już istnieje!";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "Zapisano!";
    }
}

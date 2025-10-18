package com.lightserver.backend.controller;

import com.lightserver.backend.model.User;
import com.lightserver.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return "Zapisano!";
    }

    @GetMapping("/changepassword")
    public String changePassword(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String newpassword
    ) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(bCryptPasswordEncoder.matches(password, user.getPassword())){
                if(!bCryptPasswordEncoder.matches(newpassword, user.getPassword())){
                    user.setPassword(bCryptPasswordEncoder.encode(newpassword));
                    userRepository.save(user);
                    return "Zmieniono hasło!";
                }
                return "Hasło nie może być takie samo jak obecne!";
            }
            return "Podane hasło jest nieprawidłowe!";
        }
        return "Nie znaleziono użytkownika!";
    }
}

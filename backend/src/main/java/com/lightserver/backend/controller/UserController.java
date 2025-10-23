package com.lightserver.backend.controller;

import com.lightserver.backend.DTO.LoginRequest;
import com.lightserver.backend.DTO.LoginResponse;
import com.lightserver.backend.DTO.ChangePasswordRequest;
import com.lightserver.backend.model.User;
import com.lightserver.backend.repository.UserRepository;
import com.lightserver.backend.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private  final JwtService jwtService;

    public UserController(UserRepository userRepository,JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/getallusers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/new")
    public String addUser(@RequestBody LoginRequest loginRequest) {
        if (userRepository.findByUsername(loginRequest.getUsername()).isPresent()) {
            return "Użytkownik o tej nazwie już istnieje!";
        }
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(loginRequest.getPassword()));
        userRepository.save(user);
        return "Zapisano!";
    }

    @PostMapping("/changepassword")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String username = jwtService.extractUsername(changePasswordRequest.getToken());
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "Nie znaleziono użytkownika!";
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
            return "Podane hasło jest nieprawidłowe!";
        }

        if (bCryptPasswordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            return "Hasło nie może być takie samo jak obecne!";
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "Zmieniono hasło!";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                String token = jwtService.generateToken(user.getUsername());
                return new LoginResponse("Zalogowano",token);
            }
        }
        return new LoginResponse("Błędny login lub hasło!",null);
    }

    @PostMapping("/getusername")
    public String getUsername(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        return jwtService.extractUsername(token);
    }
}

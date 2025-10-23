package com.lightserver.backend.controller;

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

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public class LoginResponse {
        private String status;   // "Zalogowano" lub "Błędne dane"
        private String token;    // JWT token

        public LoginResponse(String status, String token) {
            this.status = status;
            this.token = token;
        }

        public String getStatus() { return status; }
        public String getToken() { return token; }
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
    public String GetUsername(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        return jwtService.extractUsername(token);
    }
}

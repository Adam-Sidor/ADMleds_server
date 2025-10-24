package com.lightserver.backend.DTO;

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
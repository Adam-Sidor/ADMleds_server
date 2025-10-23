package com.lightserver.backend.DTO;

public class ChangePasswordRequest {
    private String password;
    private String newPassword;
    private String token;
    public String getPassword() {return password;}
    public String getNewPassword() {return newPassword;}
    public String getToken() {return token;}
}
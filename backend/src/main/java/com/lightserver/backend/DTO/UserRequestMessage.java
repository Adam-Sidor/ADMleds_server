package com.lightserver.backend.DTO;

import java.util.Map;

public class UserRequestMessage {
    private String token;
    private Map<String,String> commands;
    UserRequestMessage(String token, Map<String,String> commands) {
        this.token = token;
        this.commands = commands;
    }
    public String getToken() {return token;}
    public Map<String, String> getCommands() {return commands;}
}

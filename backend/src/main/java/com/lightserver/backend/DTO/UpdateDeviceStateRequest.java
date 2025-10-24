package com.lightserver.backend.DTO;

public class UpdateDeviceStateRequest {
    private String token;
    private String deviceIp;
    private String newState;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getDeviceIp() { return deviceIp; }
    public void setDeviceIp(String deviceIp) { this.deviceIp = deviceIp; }

    public String getNewState() { return newState; }
    public void setNewState(String newState) { this.newState = newState; }
}

package com.lightserver.backend.DTO;

public class NewUserSettingRequest {
    private String token;
    private int deviceId;
    private int deviceStateId;
    private String name;
    private String icon;

    public String getToken() {return token;}
    public int getDeviceId() {return deviceId;}
    public int getDeviceStateId() {return deviceStateId;}
    public String getName() {return name;}
    public String getIcon() {return icon;}

    public void setToken(String token) {this.token = token;}
    public void setDeviceId(int deviceId) {this.deviceId = deviceId;}
    public void setDeviceStateId(int deviceStateId) {this.deviceStateId = deviceStateId;}
    public void setName(String name) {this.name = name;}
    public void setIcon(String icon) {this.icon = icon;}
}
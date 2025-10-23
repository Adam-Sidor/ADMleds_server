package com.lightserver.backend.DTO;

public class NewUserSettingRequest {
    private String username;
    private int deviceId;
    private int deviceStateId;
    private String name;
    private String icon;

    public String getUsername() {return username;}
    public int getDeviceId() {return deviceId;}
    public int getDeviceStateId() {return deviceStateId;}
    public String getName() {return name;}
    public String getIcon() {return icon;}

    public void setUsername(String username) {this.username = username;}
    public void setDeviceId(int deviceId) {this.deviceId = deviceId;}
    public void setDeviceStateId(int deviceStateId) {this.deviceStateId = deviceStateId;}
    public void setName(String name) {this.name = name;}
    public void setIcon(String icon) {this.icon = icon;}
}
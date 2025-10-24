package com.lightserver.backend.DTO;

import com.lightserver.backend.model.UserSetting;

public class UserSettingDTO { //DTO - data transfer object
    private String username;
    private String deviceIp;
    private String deviceType;
    private String deviceState;
    private String name;
    private String icon;


    public UserSettingDTO(UserSetting setting) {
        this.username = setting.getUser().getUsername();
        this.deviceIp = setting.getDevice().getIpAddress();
        this.deviceType = setting.getDevice().getDeviceType().getType();
        this.deviceState = setting.getDeviceState().getStateName();
        this.name = setting.getName();
        this.icon = setting.getIcon();
    }

    public String getUsername() { return username; }
    public String getDeviceIp() { return deviceIp; }
    public String getDeviceType() { return deviceType; }
    public String getDeviceState() { return deviceState; }
    public String getName() { return name; }
    public String getIcon() { return icon; }
}
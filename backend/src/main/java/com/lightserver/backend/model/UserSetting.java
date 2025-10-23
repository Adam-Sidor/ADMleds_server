package com.lightserver.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSetting {

    @EmbeddedId
    private UserSettingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("username")
    @JoinColumn(name = "users_username", referencedColumnName = "username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("deviceId")
    @JoinColumn(name = "iot_devices_device_id", referencedColumnName = "device_id")
    private IoTDevice device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_states_state_id")
    private DeviceState deviceState;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon", nullable = false)
    private String icon;

    public UserSettingId getId() {
        return id;
    }

    public void setId(UserSettingId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IoTDevice getDevice() {
        return device;
    }

    public void setDevice(IoTDevice device) {
        this.device = device;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

package com.lightserver.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSettingId implements Serializable {

    @Column(name = "users_username")
    private String username;

    @Column(name = "iot_devices_device_id")
    private int deviceId;

    public UserSettingId() {}

    public UserSettingId(String username, int deviceId) {
        this.username = username;
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public int getDeviceId() {
        return deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSettingId)) return false;
        UserSettingId that = (UserSettingId) o;
        return deviceId == that.deviceId && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, deviceId);
    }
}

package com.lightserver.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "device_types")
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_types_id")
    private int deviceTypesId;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "description")
    private String description;

    public int getDeviceTypesId() {
        return deviceTypesId;
    }

    public void setDeviceTypesId(int deviceTypesId) {
        this.deviceTypesId = deviceTypesId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

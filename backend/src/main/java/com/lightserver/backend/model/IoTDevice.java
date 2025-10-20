package com.lightserver.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "iot_devices")
public class IoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "ip_address", unique = true, nullable = false, length = 15)
    private String ipAddress;

    @Column(name = "device_types_type_id", nullable = false)
    private Integer deviceTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_types_type_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private DeviceType deviceType;

    public Integer getDeviceId() { return deviceId; }
    public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Integer getDeviceTypeId() { return deviceTypeId; }
    public void setDeviceTypeId(Integer deviceTypeId) { this.deviceTypeId = deviceTypeId; }

    public DeviceType getDeviceType() { return deviceType; }
    public void setDeviceType(DeviceType deviceType) { this.deviceType = deviceType; }
}

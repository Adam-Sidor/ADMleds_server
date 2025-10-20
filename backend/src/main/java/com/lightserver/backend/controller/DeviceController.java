package com.lightserver.backend.controller;

import com.lightserver.backend.model.IoTDevice;
import com.lightserver.backend.repository.IoTDeviceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
    private final IoTDeviceRepository iotDeviceRepository;
    public DeviceController(IoTDeviceRepository iotDeviceRepository) {
        this.iotDeviceRepository = iotDeviceRepository;
    }
    @GetMapping("/getalldevices")
    public List<IoTDevice> getAllDevices() {
        return iotDeviceRepository.findAll();
    }
    @GetMapping("/new")
    public String addUser(
            @RequestParam String ip,
            @RequestParam int type
    ) {
        if (iotDeviceRepository.findByIpAddress(ip).isPresent()) {
            return "Urządzenie o adresie "+ip+" już istnieje!";
        }
        IoTDevice device = new IoTDevice();
        device.setIpAddress(ip);
        device.setDeviceTypeId(type);
        iotDeviceRepository.save(device);
        return "Zapisano!";
    }
}

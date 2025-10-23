package com.lightserver.backend.controller;

import com.lightserver.backend.model.IoTDevice;
import com.lightserver.backend.repository.IoTDeviceRepository;
import org.springframework.web.bind.annotation.*;

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

    public static class CreateDeviceRequest {
        private String ip;
        private int type;

        public String getIp() {return ip;}
        public int getType() {return type;}
    }

    @PostMapping("/new")
    public String addUser(@RequestBody CreateDeviceRequest createDeviceRequest) {
        if (iotDeviceRepository.findByIpAddress(createDeviceRequest.getIp()).isPresent()) {
            return "IP error";
        }
        IoTDevice device = new IoTDevice();
        device.setIpAddress(createDeviceRequest.getIp());
        device.setDeviceTypeId(createDeviceRequest.getType());
        iotDeviceRepository.save(device);
        System.out.println("Create Device");
        return "Zapisano!";
    }
}

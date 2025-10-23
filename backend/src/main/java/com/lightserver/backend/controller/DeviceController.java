package com.lightserver.backend.controller;

import com.lightserver.backend.model.DeviceType;
import com.lightserver.backend.model.IoTDevice;
import com.lightserver.backend.repository.DeviceTypeRepository;
import com.lightserver.backend.repository.IoTDeviceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
    private final IoTDeviceRepository iotDeviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    public DeviceController(IoTDeviceRepository iotDeviceRepository, DeviceTypeRepository deviceTypeRepository) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }
    @PostMapping("/getalldevices")
    public List<IoTDevice> getAllDevices() {
        return iotDeviceRepository.findAll();
    }

    @PostMapping("/getalltypes")
    public List<DeviceType> getAllTypes() {
        return deviceTypeRepository.findAll();
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
        return "Zapisano!";
    }
}

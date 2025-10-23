package com.lightserver.backend.controller;

import com.lightserver.backend.DTO.NewUserSettingRequest;
import com.lightserver.backend.DTO.UserSettingDTO;
import com.lightserver.backend.model.UserSetting;
import com.lightserver.backend.model.UserSettingId;
import com.lightserver.backend.repository.DeviceStateRepository;
import com.lightserver.backend.repository.IoTDeviceRepository;
import com.lightserver.backend.repository.UserRepository;
import com.lightserver.backend.repository.UserSettingsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usersettings")
public class UserSettingsController {
    private final UserSettingsRepository userSettingsRepository;
    private final UserRepository userRepository;
    private final IoTDeviceRepository ioTDeviceRepository;
    private final DeviceStateRepository deviceStateRepository;

    public UserSettingsController(UserSettingsRepository userSettingsRepository, UserRepository userRepository, IoTDeviceRepository ioTDeviceRepository, DeviceStateRepository deviceStateRepository) {
        this.userSettingsRepository = userSettingsRepository;
        this.userRepository = userRepository;
        this.ioTDeviceRepository = ioTDeviceRepository;
        this.deviceStateRepository = deviceStateRepository;

    }

    @PostMapping("/getalldevices")
    public List<UserSettingDTO> getAllDevices() {
        return userSettingsRepository.findAll().stream()
                .map(UserSettingDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public String newUserSetting(@RequestBody NewUserSettingRequest req) {
        UserSetting setting = new UserSetting();
        setting.setId(new UserSettingId(req.getUsername(), req.getDeviceId()));
        setting.setUser(userRepository.findByUsername(req.getUsername()).orElseThrow());
        setting.setDevice(ioTDeviceRepository.findById(req.getDeviceId()).orElseThrow());
        setting.setDeviceState(deviceStateRepository.findById(req.getDeviceStateId()).orElseThrow());
        setting.setName(req.getName());
        setting.setIcon(req.getIcon());
        userSettingsRepository.save(setting);
        return "Zapisano!";
    }

}

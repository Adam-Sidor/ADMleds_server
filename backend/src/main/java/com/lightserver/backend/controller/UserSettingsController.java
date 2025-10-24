package com.lightserver.backend.controller;

import com.lightserver.backend.DTO.NewUserSettingRequest;
import com.lightserver.backend.DTO.UpdateDeviceStateRequest;
import com.lightserver.backend.DTO.UserSettingDTO;
import com.lightserver.backend.model.DeviceState;
import com.lightserver.backend.model.User;
import com.lightserver.backend.model.UserSetting;
import com.lightserver.backend.model.UserSettingId;
import com.lightserver.backend.repository.DeviceStateRepository;
import com.lightserver.backend.repository.IoTDeviceRepository;
import com.lightserver.backend.repository.UserRepository;
import com.lightserver.backend.repository.UserSettingsRepository;
import com.lightserver.backend.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usersettings")
public class UserSettingsController {
    private final UserSettingsRepository userSettingsRepository;
    private final UserRepository userRepository;
    private final IoTDeviceRepository ioTDeviceRepository;
    private final DeviceStateRepository deviceStateRepository;
    private final JwtService jwtService;

    public UserSettingsController(UserSettingsRepository userSettingsRepository, UserRepository userRepository, IoTDeviceRepository ioTDeviceRepository, DeviceStateRepository deviceStateRepository, JwtService jwtService) {
        this.userSettingsRepository = userSettingsRepository;
        this.userRepository = userRepository;
        this.ioTDeviceRepository = ioTDeviceRepository;
        this.deviceStateRepository = deviceStateRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/getalldevices")
    public List<UserSettingDTO> getAllDevices() {
        return userSettingsRepository.findAll().stream()
                .map(UserSettingDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/getuserdevices")
    public List<UserSettingDTO> getUserDevices(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        String username = jwtService.extractUsername(token);
        return userSettingsRepository.findByUser_Username(username).stream()
                .map(UserSettingDTO::new)
                .collect(Collectors.toList());

    }

    @PostMapping("/new")
    public String newUserSetting(@RequestBody NewUserSettingRequest req) {
        String username = jwtService.extractUsername(req.getToken());
        UserSetting setting = new UserSetting();
        setting.setId(new UserSettingId(username, req.getDeviceId()));
        setting.setUser(userRepository.findByUsername(username).orElseThrow());
        setting.setDevice(ioTDeviceRepository.findById(req.getDeviceId()).orElseThrow());
        setting.setDeviceState(deviceStateRepository.findById(req.getDeviceStateId()).orElseThrow());
        setting.setName(req.getName());
        setting.setIcon(req.getIcon());
        userSettingsRepository.save(setting);
        return "Zapisano!";
    }

    @PostMapping("/updatestate")
    public String updateDeviceState(@RequestBody UpdateDeviceStateRequest request) {
        String username = jwtService.extractUsername(request.getToken());

        Optional<UserSetting> optionalSetting = userSettingsRepository
                .findByUser_UsernameAndDevice_IpAddress(username, request.getDeviceIp());

        if (optionalSetting.isEmpty()) {
            return "Nie znaleziono urządzenia użytkownika";
        }

        UserSetting setting = optionalSetting.get();
        Optional<DeviceState> newDeviceState = deviceStateRepository.findByStateName(request.getNewState());

        if (newDeviceState.isEmpty()) {
            return "Nie znaleziono stanu urządzenia: " + request.getNewState();
        }

        setting.setDeviceState(newDeviceState.get());
        userSettingsRepository.save(setting);

        return "Zaktualizowano stan urządzenia na: " + request.getNewState();
    }
}

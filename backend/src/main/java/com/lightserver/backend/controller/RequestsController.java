package com.lightserver.backend.controller;

import com.lightserver.backend.DTO.UserRequestMessage;
import com.lightserver.backend.model.UserSetting;
import com.lightserver.backend.repository.UserSettingsRepository;
import com.lightserver.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class RequestsController {

    private final WebClient webClient = WebClient.create();
    private  final JwtService jwtService;
    private final UserSettingsRepository userSettingsRepository;

    public RequestsController(JwtService jwtService, UserSettingsRepository userSettingsRepository) {
        this.jwtService = jwtService;
        this.userSettingsRepository = userSettingsRepository;
    }

    @PostMapping("/sendrequest")
    public String sendRequest(@RequestBody UserRequestMessage userRequestMessage){

        String username = jwtService.extractUsername(userRequestMessage.getToken());
        List<UserSetting> userSetting = userSettingsRepository.findByUser_UsernameAndDeviceState_StateId(username,4);
        for (UserSetting userSetting1 : userSetting) {
            Map<String, String> commands = userRequestMessage.getCommands();
            String ip = userSetting1.getDevice().getIpAddress();
            for (Map.Entry<String, String> entry : commands.entrySet()) {
                String command = entry.getKey();
                String value = entry.getValue();
                String url = "http://" + ip + "/" + command+"="+value;
                System.out.println(url);
                try {
                    String response = webClient.get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                    System.out.println("Response from " + ip + ": </br>" + response);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        return "";
    }
}

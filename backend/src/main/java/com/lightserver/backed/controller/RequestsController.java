package com.lightserver.backed.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class RequestsController {

    private final WebClient webClient = WebClient.create();

    @GetMapping("/sendrequest")
    public ResponseEntity<String> sendRequest(
            @RequestParam String ip,
            @RequestParam(defaultValue = "8080") int port,
            @RequestParam(defaultValue = "/api/game/start") String path
    ){

        String url = "http://" + ip + ":" + port + path;

        Map<String, Object> body = Map.of(
                "sessionId", 1,
                "rows", 10,
                "cols", 10,
                "mines", 5
        );

        try {
            String response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok("Response from " + ip + ": </br>" + response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .body("Error response: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}

package com.sarthi.service.Impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CrisAuthServic {

    @Value("${cris.base-url}")
    private String baseUrl;

    @Value("${cris.username}")
    private String username;

    @Value("${cris.password}")
    private String password;

    private final RestTemplate restTemplate = new RestTemplate();

    private String token; // cache in memory

    public synchronized String getToken() {

        if (token != null) {
            return token;
        }

        String url = baseUrl + "/authenticate";

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, body, Map.class);

        // CRIS returns JWT in "token"
        token = (String) response.getBody().get("token");
        System.out.print(token);

        return token;
    }
}



package com.sarthi.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CrisPoListService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, String>> getPoList(String date) {

        String url = baseUrl + "/purchase/getPOList";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authService.getToken());
        Map<String, String> body = new HashMap<>();
        body.put("poDate", date.toString());

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);
        System.out.print(response);

        return (List<Map<String, String>>) response.getBody().get("data");


    }
}

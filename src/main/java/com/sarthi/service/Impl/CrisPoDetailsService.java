package com.sarthi.service.Impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.config.RequestConfig;

@Service
public class CrisPoDetailsService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor injection for the configured RestTemplate
    public CrisPoDetailsService(RestTemplate crisRestTemplate) {
        this.restTemplate = crisRestTemplate;
    }

    public Map<String, Object> getPoDetails(String rly, String poKey) throws JsonProcessingException {
        String url = baseUrl + "/purchase/getPODetails";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authService.getToken());

        Map<String, String> body = new HashMap<>();
        body.put("rly", rly);
        body.put("poKey", poKey);

        String jsonBody = objectMapper.writeValueAsString(body);
        System.out.println("REQUEST JSON: " + jsonBody);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("RAW PO DETAILS RESPONSE = " + response.getBody());

        if (response.getBody() == null || !response.getBody().trim().startsWith("{")) {
            throw new RuntimeException("CRIS returned HTML instead of JSON");
        }

        Map<String, Object> json = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        return (Map<String, Object>) json.get("data");
    }
}

// Separate @Configuration class for the RestTemplate bean
@Configuration
class CrisRestTemplateConfig {

    @Bean
    public RestTemplate crisRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Disable Expect: 100-continue
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setExpectContinueEnabled(false)
                                .build()
                )
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);

        return restTemplate;
    }
}


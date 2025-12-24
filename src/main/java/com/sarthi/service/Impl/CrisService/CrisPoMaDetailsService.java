package com.sarthi.service.Impl.CrisService;

import com.sarthi.service.Impl.CrisAuthServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CrisPoMaDetailsService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate crisRestTemplate;

    public Map<String, Object> getMaDetails(String rly, String maKey) {

        String url = baseUrl + "/purchase/getPoMaDetails";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authService.getToken());

        Map<String, String> body = Map.of(
                "rly", rly,
                "maKey", maKey
        );

        ResponseEntity<Map> response =
                crisRestTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

        return (Map<String, Object>) response.getBody().get("data");
    }
}

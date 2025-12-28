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

import java.util.List;
import java.util.Map;

@Service
public class CrisPoMaListService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate crisRestTemplate;

    public List<Map<String, String>> getMaList(String date) {

        String url = baseUrl + "/purchase/getPoMaList";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authService.getToken());

        Map<String, String> body = Map.of("poMaDate", date);

        ResponseEntity<Map> response =
                crisRestTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

        return (List<Map<String, String>>) response.getBody().get("data");
    }
}


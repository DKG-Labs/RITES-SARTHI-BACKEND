package com.sarthi.service.Impl.CrisService;

import com.sarthi.service.Impl.CrisAuthServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;

@Service
public class CrisPoCancellationDetailsService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate crisRestTemplate;

    public Map<String, Object> getDetails(String rly, String caKey) {

        String url = baseUrl + "/purchase/getPOCancellationDetail";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authService.getToken());

        Map<String, String> body = Map.of(
                "rly", rly,
                "caKey", caKey
        );

        ResponseEntity<Map> response =
                crisRestTemplate.postForEntity(
                        url,
                        new HttpEntity<>(body, headers),
                        Map.class
                );

        return (Map<String, Object>) response.getBody().get("data");
    }
}


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
public class CrisAmendedPoDetailsService {

    @Autowired
    private CrisAuthServic authService;

    @Value("${cris.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate crisRestTemplate;

    public Map<String, Object> getDetails(String rly, String poKey)
            throws Exception {

        String url = baseUrl + "/purchase/getAmendedPODetails";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authService.getToken());

        Map<String, String> body = Map.of(
                "rly", rly,
                "poKey", poKey
        );

        HttpEntity<?> request = new HttpEntity<>(body, headers);

//        ResponseEntity<String> response =
//                crisRestTemplate.postForEntity(url, request, String.class);
//
//        CrisApiResponseDTO dto =
//                mapper.readValue(response.getBody(), CrisApiResponseDTO.class);

        ResponseEntity<Map> response =
                crisRestTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

      //  return dto.getData();
        return (Map<String, Object>) response.getBody().get("data");
    }
}


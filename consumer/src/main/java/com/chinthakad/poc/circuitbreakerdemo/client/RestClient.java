package com.chinthakad.poc.circuitbreakerdemo.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

    private RestTemplate restTemplate = new RestTemplate();

    public String consume() {
        System.out.println("Consume Direct");
        ResponseEntity<String> responseEnt = restTemplate.getForEntity("http://localhost:9000/produce", String.class);
        if (responseEnt.getStatusCode().is2xxSuccessful()) {
            return responseEnt.getBody();
        } else {
            throw new RuntimeException("Error Occurred");
        }
    }

    public String consumeAlternative() {
        System.out.println("Consume Alternate");
        ResponseEntity<String> responseEnt = restTemplate.getForEntity("http://localhost:9000/produce/alternative", String.class);
        if (responseEnt.getStatusCode().is2xxSuccessful()) {
            return responseEnt.getBody();
        } else {
            throw new RuntimeException("Error Occurred");
        }
    }
}

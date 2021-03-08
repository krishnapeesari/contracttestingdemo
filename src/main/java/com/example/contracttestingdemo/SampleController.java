package com.example.contracttestingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SampleController {
    private final RestTemplate restTemplate;
    private final String serviceUrl;

    @Autowired
    public SampleController(@Value("${service.url}") final String serviceUrl) {
        this.restTemplate = new RestTemplateBuilder().build();
        this.serviceUrl = serviceUrl;
    }

    @GetMapping("/sample/api")
    public String sampleApi(@RequestParam("id") String id) {
        ServiceResponse serviceResponse = restTemplate.getForObject(serviceUrl + "/" + id, ServiceResponse.class);
        assert serviceResponse != null;
        return serviceResponse.getData().getFirstName() + " " + serviceResponse.getData().getLastName();
    }
}

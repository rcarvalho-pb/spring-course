package com.example.demo.sec01;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("traditional")
public class TradicionalWebController {
    private static final Logger log = LoggerFactory.getLogger(TradicionalWebController.class);

    private final RestClient restClient = RestClient.builder()
                                                .baseUrl("http://localhost:7070")                        
                                                .build();

    @GetMapping("products")
    public List<Product> getProducts() {
        List<Product> list = this.restClient.get()
            .uri("/demo01/products")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Product>>() {});
        log.info("received response: {}", list);
        return list;
    }
}

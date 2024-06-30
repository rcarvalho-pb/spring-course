package com.example.demo.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
@EnableWebFlux
public class ReactiveWebController {

    private final static Logger log = LoggerFactory.getLogger(ReactiveWebController.class);

    private final WebClient webClient = WebClient.builder()
                                            .baseUrl("http://localhost:7070")
                                            .build();
    
    @GetMapping("products")
    public Flux<Product> getProducts() {
        return this.webClient.get()
                    .uri("/demo01/products/notorious")
                    .retrieve()
                    .bodyToFlux(Product.class)
                    .onErrorComplete()
                    .doOnNext(p -> log.info("received: {}", p));
    }

    @GetMapping(value = "products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStream() {
        return this.webClient.get()
                    .uri("/demo01/products/notorious")
                    .retrieve()
                    .bodyToFlux(Product.class)
                    .onErrorComplete()
                    .doOnNext(p -> log.info("received: {}", p));
    }
}

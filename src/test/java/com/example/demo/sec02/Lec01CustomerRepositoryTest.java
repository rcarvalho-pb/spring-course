package com.example.demo.sec02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.sec02.entity.Customer;
import com.example.demo.sec02.repository.CustomerRepository;

import reactor.test.StepVerifier;


public class Lec01CustomerRepositoryTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    private final CustomerRepository repository;
    
    @Autowired
    public Lec01CustomerRepositoryTest(CustomerRepository repository) {
        this.repository = repository;
    }

    @Test
    public void findAll() {
        this.repository.findAll()
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .expectNextCount(10)
            .expectComplete()
            .verify();
    }

    @Test
    public void findById() {
        this.repository.findById(2)
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
            .expectComplete()
            .verify();
    }

    @Test
    public void findByName() {
        this.repository.findByName("jake")
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
            .expectComplete()
            .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        this.repository.findByEmailEndingWith("ke@gmail.com")
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .assertNext(c -> Assertions.assertEquals("mike@gmail.com", c.getEmail()))
            .as("First customer should be Mike")
            .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
            .as("Second customer should be jake")
            .expectComplete()
            .verify();
    }

    
    @Test
    public void insertAndDeleteCustomer() {
        var customer = Customer.builder()
            .name("marshal")
            .email("marshal@gmail.com")
            .build();

        this.repository.save(customer)
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .assertNext(c -> Assertions.assertNotNull(c.getId()))
            .expectComplete()
            .verify();

        this.repository.count()
            .as(StepVerifier::create)
            .expectNext(11L)
            .expectComplete()
            .verify();

        this.repository.deleteById(11)
            .then(this.repository.count())
            .as(StepVerifier::create)
            .expectNext(10L)
            .expectComplete()
            .verify();

    }

    @Test
    public void updateCustomer() {
        this.repository.findByName("ethan")
            .doOnNext(c -> c.setName("noel"))
            .flatMap(c -> this.repository.save(c))
            .doOnNext(c -> log.info("{}", c))
            .as(StepVerifier::create)
            .assertNext(c -> Assertions.assertNotNull(c.getId()))
            .expectComplete()
            .verify();
    }
}

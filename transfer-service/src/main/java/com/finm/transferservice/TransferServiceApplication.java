package com.finm.transferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
public class TransferServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class, args);
    }
}
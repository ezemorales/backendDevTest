package com.em.apiproductschallenge;

import com.em.apiproductschallenge.client.MockRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {MockRestClient.class})
public class ApiProductsChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProductsChallengeApplication.class, args);
    }

}

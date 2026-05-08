package com.brokerskip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BrokerSkipApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokerSkipApplication.class, args);
    }

}

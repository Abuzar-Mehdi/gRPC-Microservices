package com.learning.microservices.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ConfigMain {
    public static void main(String[] args) {
        SpringApplication.run(ConfigMain.class,args);
    }
}
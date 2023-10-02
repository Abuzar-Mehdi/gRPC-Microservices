package com.learning.microservices.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class GrpcServerMain {
    public static void main(String[] args) {

        SpringApplication.run(GrpcServerMain.class,args);
    }
}
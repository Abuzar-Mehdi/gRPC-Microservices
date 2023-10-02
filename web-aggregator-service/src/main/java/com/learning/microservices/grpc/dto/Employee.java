package com.learning.microservices.grpc.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Employee {

    private Long Id;
    private String firstName;
    private String LastName;
    private String email;
    private String departmentCode;
    private String organizationCode;

}

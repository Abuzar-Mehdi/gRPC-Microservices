package com.learning.microservices.grpc.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Department {

    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}

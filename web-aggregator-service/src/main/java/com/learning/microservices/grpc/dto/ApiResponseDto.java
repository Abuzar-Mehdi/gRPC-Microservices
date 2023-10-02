package com.learning.microservices.grpc.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDto {

    private Employee employee;
    private Department department;
    private Organization organization;

}

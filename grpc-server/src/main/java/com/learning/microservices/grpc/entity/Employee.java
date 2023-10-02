package com.learning.microservices.grpc.entity;


import javax.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String LastName;
    @Column(nullable = false,unique = true)
    private String email;
    private String departmentCode;

    private String organizationCode;

}

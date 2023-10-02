package com.learning.microservices.grpc.controller;


import com.google.protobuf.Descriptors;
import com.learning.microservices.grpc.ApiResponse;
import com.learning.microservices.grpc.EmployeeDto;
import com.learning.microservices.grpc.dto.ApiResponseDto;
import com.learning.microservices.grpc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Map<Descriptors.FieldDescriptor,Object>> saveEmployee(@RequestBody EmployeeDto employeeDto){

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee.getAllFields(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponseDto> getEmployeeById(@PathVariable("id") Long id){

        ApiResponseDto apiResponseDto = employeeService.getEmployeeById(id);

        return new ResponseEntity<>(apiResponseDto,HttpStatus.OK);
    }
}

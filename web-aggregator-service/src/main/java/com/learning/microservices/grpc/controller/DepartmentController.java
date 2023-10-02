package com.learning.microservices.grpc.controller;

import com.google.protobuf.Descriptors;
import com.learning.microservices.grpc.DepartmentDto;
import com.learning.microservices.grpc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Map<Descriptors.FieldDescriptor,Object>> saveDepartment(@RequestBody DepartmentDto departmentdto){

        DepartmentDto savedDepartment =departmentService.saveDepartment(departmentdto);
       return new ResponseEntity<>(savedDepartment.getAllFields(), HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Map<Descriptors.FieldDescriptor,Object>> getDepartmentByCode(@PathVariable("id")  long id){

        DepartmentDto departmentdto = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(departmentdto.getAllFields(),HttpStatus.OK);
    }
}

package com.learning.microservices.grpc.controller;


import com.google.protobuf.Descriptors;
import com.learning.microservices.grpc.OrganizationDto;
import com.learning.microservices.grpc.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/organizations")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<Map<Descriptors.FieldDescriptor, Object>> saveOrganization(@RequestBody OrganizationDto organizationDto){

        OrganizationDto savedOrganization=organizationService.saveOrganization(organizationDto);

        return new ResponseEntity<>(savedOrganization.getAllFields(), HttpStatus.CREATED);
    }

   /* @GetMapping("{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("id") Long id){

        OrganizationDto saveOrganizationDto = organizationService.getOrganizationById(id);

        return new ResponseEntity<>(saveOrganizationDto,HttpStatus.OK);
    }*/

    @GetMapping("{code}")
    public ResponseEntity<Map<Descriptors.FieldDescriptor, Object>> getOrganizationByCode(@PathVariable("code") String code){

        OrganizationDto saveOrganizationDto = organizationService.findByOrganizationCode(code);

        return new ResponseEntity<>(saveOrganizationDto.getAllFields(),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Map<Descriptors.FieldDescriptor,Object>>> getAllOrganizations(){

        List<OrganizationDto> organizationDtoList = organizationService.getAllOrganization();

        List<Map<Descriptors.FieldDescriptor,Object>> response = new ArrayList<>();

        organizationDtoList.forEach(
                organizationDto -> response.add(organizationDto.getAllFields())
        );

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
}

package com.learning.microservices.grpc.mapper;

import com.learning.microservices.grpc.DepartmentDto;
import com.learning.microservices.grpc.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department department){

        DepartmentDto departmentDto = DepartmentDto.newBuilder()
                .setDepartmentCode(department.getDepartmentCode())
                .setDepartmentName(department.getDepartmentName())
                .setDepartmentDescription(department.getDepartmentDescription())
                .setId(department.getId())
                .build();

        return  departmentDto;

    }

    public static Department mapToDepartment(DepartmentDto departmentDto){

        Department department = Department.builder()
                .departmentCode(departmentDto.getDepartmentCode())
                .departmentName(departmentDto.getDepartmentName())
                .departmentDescription(departmentDto.getDepartmentDescription())
                .id(departmentDto.getId())
                .build();

        return department;
    }
}

package com.learning.microservices.grpc.mapper;

import com.learning.microservices.grpc.EmployeeDto;
import com.learning.microservices.grpc.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){

        EmployeeDto employeeDto = EmployeeDto.newBuilder()
                .setDepartmentCode(employee.getDepartmentCode())
                .setEmail(employee.getEmail())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setId(employee.getId())
                .setOrganizationCode(employee.getOrganizationCode())
                .build();

        return  employeeDto;

    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){

       Employee employee = Employee.builder()
               .departmentCode(employeeDto.getDepartmentCode())
               .email(employeeDto.getEmail())
               .firstName(employeeDto.getFirstName())
               .LastName(employeeDto.getLastName())
               .Id(employeeDto.getId())
               .organizationCode(employeeDto.getOrganizationCode())
               .build();

        return employee;
    }
}

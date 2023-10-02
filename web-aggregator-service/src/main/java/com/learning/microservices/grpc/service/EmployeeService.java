package com.learning.microservices.grpc.service;


import com.learning.microservices.grpc.*;
import com.learning.microservices.grpc.dto.ApiResponseDto;
import com.learning.microservices.grpc.mapper.DepartmentMapper;
import com.learning.microservices.grpc.mapper.EmployeeMapper;
import com.learning.microservices.grpc.mapper.OrganizationMapper;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @GrpcClient("grpc-server")
    private EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub;
    private final DepartmentService departmentService;
    private final OrganizationService organizationService;

    public EmployeeService(DepartmentService departmentService, OrganizationService organizationService) {
        this.departmentService = departmentService;
        this.organizationService = organizationService;
    }

//    @GrpcClient("department-service")
//    private DepartmentServiceGrpc.DepartmentServiceBlockingStub departmentServiceBlockingStub;
//    @GrpcClient("organization-service")
//    private OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub;


    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        EmployeeSaveRequest employeeSaveRequest = EmployeeSaveRequest.newBuilder()
                .setEmployeeDto(employeeDto)
                .build();

        EmployeeSaveResponse employeeSaveResponse=
                employeeServiceBlockingStub.saveEmployee(employeeSaveRequest);

        return employeeSaveResponse.getEmployeeDto();

    }

   // @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment" )
   // @Retry(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment" )
    //@Retry(name = "${spring.application.name}" )
    public ApiResponseDto getEmployeeById(Long id) {

        LOGGER.info("inside getEmployeeById method");

        EmployeeSearchRequest employeeSearchRequest = EmployeeSearchRequest.newBuilder()
                .setId(id)
                .build();

        EmployeeSearchResponse employeeSearchResponse
                = employeeServiceBlockingStub.getEmployee(employeeSearchRequest);
        EmployeeDto employeeDto = employeeSearchResponse.getEmployeeDto();

        DepartmentDto departmentDto
                = departmentService.getDepartmentByCode(employeeDto.getDepartmentCode());

        OrganizationDto organizationDto
                = organizationService.findByOrganizationCode(employeeDto.getOrganizationCode());

        return ApiResponseDto.builder()
                .employee(EmployeeMapper.mapToEmployee(employeeDto))
                .department(DepartmentMapper.mapToDepartment(departmentDto))
                .organization(OrganizationMapper.mapToOrganization(organizationDto))
                .build();
    }

    public ApiResponseDto getDefaultDepartment(Long id, Exception exception) {

        LOGGER.info("inside getDefaultDepartment method");

        EmployeeSearchRequest employeeSearchRequest = EmployeeSearchRequest.newBuilder()
                .setId(id)
                .build();

        EmployeeSearchResponse employeeSearchResponse
                = employeeServiceBlockingStub.getEmployee(employeeSearchRequest);
        EmployeeDto employeeDto = employeeSearchResponse.getEmployeeDto();

        DepartmentDto departmentDto = DepartmentDto.newBuilder()
                .setDepartmentCode("RD001")
                .setDepartmentDescription("Research & Development")
                .setDepartmentName("R&D")
                .build();

        OrganizationDto organizationDto = OrganizationDto.newBuilder()
                .setOrganizationName("Bank Al Habib Ltd.")
                .setOrganizationCode("BAHL001")
                .setOrganizationDescription("Banking & Financial")
                .build();


        return ApiResponseDto.builder()
                .employee(EmployeeMapper.mapToEmployee(employeeDto))
                .department(DepartmentMapper.mapToDepartment(departmentDto))
                .organization(OrganizationMapper.mapToOrganization(organizationDto))
                .build();

    }
}

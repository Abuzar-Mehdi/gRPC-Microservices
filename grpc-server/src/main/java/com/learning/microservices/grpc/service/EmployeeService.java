package com.learning.microservices.grpc.service;

import com.google.protobuf.Empty;
import com.learning.microservices.grpc.*;
import com.learning.microservices.grpc.entity.Employee;
import com.learning.microservices.grpc.mapper.EmployeeMapper;
import com.learning.microservices.grpc.repository.EmployeeRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void saveEmployee(EmployeeSaveRequest request, StreamObserver<EmployeeSaveResponse> responseObserver) {

        logger.info("Start save Employee : "+request.getEmployeeDto());
        Employee employee = EmployeeMapper.mapToEmployee(request.getEmployeeDto());
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employeeRepository.save(employee));

        responseObserver.onNext(EmployeeSaveResponse.newBuilder().
                setEmployeeDto(employeeDto)
                .build());
        responseObserver.onCompleted();
        logger.info("Finish save Employee : "+employeeDto);

    }

    @Override
    public void getEmployee(EmployeeSearchRequest request, StreamObserver<EmployeeSearchResponse> responseObserver) {

        logger.info("Start get Employee : "+request.getId());
        EmployeeDto employeeDto =  EmployeeMapper.mapToEmployeeDto(
                employeeRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("Record Not found")));

        responseObserver.onNext(EmployeeSearchResponse.newBuilder()
                .setEmployeeDto(employeeDto)
                .build());
        responseObserver.onCompleted();

        logger.info("Finish get Employee : "+employeeDto);
    }

    @Override
    public void updateEmployee(EmployeeUpdateRequest request, StreamObserver<EmployeeUpdateResponse> responseObserver) {

        logger.info("Start Update Employee : "+request.getEmployeeDto());

        Employee department= employeeRepository.findById(request.getEmployeeDto().getId())
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        EmployeeDto employeeDto=EmployeeMapper.mapToEmployeeDto(employeeRepository.save(department));

        responseObserver.onNext(EmployeeUpdateResponse.newBuilder()
                .setEmployeeDto(employeeDto)
                .build());
        responseObserver.onCompleted();
        logger.info("finish Update Employee : "+employeeDto);
    }

    @Override
    public void getAllEmployee(Empty request, StreamObserver<AllEmployeeSearchResponse> responseObserver) {

        logger.info("Start get all Employee : ");

        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeRepository.findAll()
                .forEach(department -> employeeDtoList.add( EmployeeMapper.mapToEmployeeDto(department)));

        responseObserver.onNext(AllEmployeeSearchResponse.newBuilder()
                .addAllEmployeeDto(employeeDtoList)
                .build());

        responseObserver.onCompleted();

        logger.info("Finish get all Employee : "+employeeDtoList.size());
    }

    @Override
    public void deleteEmployee(EmployeeDeleteRequest request, StreamObserver<EmployeeDeleteResponse> responseObserver) {

        logger.info("Start delete Employee : "+request.getId());

        Employee employee=employeeRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        employeeRepository.delete(employee);
        responseObserver.onNext(EmployeeDeleteResponse.newBuilder()
                .setStatus(1)
                .build());
        responseObserver.onCompleted();
        logger.info("finish delete Employee : "+employee);
    }
}

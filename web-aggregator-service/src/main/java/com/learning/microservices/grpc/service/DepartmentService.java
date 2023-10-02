package com.learning.microservices.grpc.service;


import com.learning.microservices.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {


    @GrpcClient("grpc-server")
    private DepartmentServiceGrpc.DepartmentServiceBlockingStub departmentServiceBlockingStub;

    public DepartmentDto saveDepartment(DepartmentDto DepartmentDto) {

        DepartmentSaveRequest departmentSaveRequest = DepartmentSaveRequest.newBuilder()
                .setDepartmentDto(DepartmentDto)
                .build();



        DepartmentSaveResponse departmentSaveResponse =departmentServiceBlockingStub.saveDepartment(departmentSaveRequest);
        return  departmentSaveResponse.getDepartmentDto().getDefaultInstanceForType();

    }

    public DepartmentDto getDepartmentById(Long id) {

        System.out.println("id = " + id);
        DepartmentSearchRequest departmentSearchRequest = DepartmentSearchRequest.newBuilder()
                .setId(id)
                .build();
        DepartmentSearchResponse departmentSearchResponse
                =departmentServiceBlockingStub.getDepartment(departmentSearchRequest);

        return departmentSearchResponse.getDepartmentDto();
    }

    public DepartmentDto getDepartmentByCode(String code){

        DepartmentByCodeRequest departmentByCodeRequest =DepartmentByCodeRequest.newBuilder()
                .setDepartmentCode(code)
                .build();
        DepartmentSearchResponse departmentSearchResponse
                = departmentServiceBlockingStub.getDepartmentByCode(departmentByCodeRequest);
        return departmentSearchResponse.getDepartmentDto();
    }
}

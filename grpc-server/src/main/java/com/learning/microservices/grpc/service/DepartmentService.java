package com.learning.microservices.grpc.service;

import com.google.protobuf.Empty;
import com.learning.microservices.grpc.*;
import com.learning.microservices.grpc.entity.Department;
import com.learning.microservices.grpc.mapper.DepartmentMapper;
import com.learning.microservices.grpc.repository.DepartmentRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class DepartmentService extends DepartmentServiceGrpc.DepartmentServiceImplBase {

    Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void saveDepartment(DepartmentSaveRequest request, StreamObserver<DepartmentSaveResponse> responseObserver) {

        logger.info("Start save Department : "+request.getDepartmentDto());
        Department department = DepartmentMapper.mapToDepartment(request.getDepartmentDto());
        DepartmentDto departmentDto = DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));

        responseObserver.onNext(DepartmentSaveResponse.newBuilder().
                setDepartmentDto(departmentDto)
                .build());
        responseObserver.onCompleted();
        logger.info("Finish save Department : "+departmentDto);
    }

    @Override
    public void getDepartment(DepartmentSearchRequest request, StreamObserver<DepartmentSearchResponse> responseObserver) {

        logger.info("Start get Department : "+request.getId());
        DepartmentDto departmentDto =  DepartmentMapper.mapToDepartmentDto(
                departmentRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("Record Not found")));

        responseObserver.onNext(DepartmentSearchResponse.newBuilder()
                        .setDepartmentDto(departmentDto)
                .build());
        responseObserver.onCompleted();

        logger.info("Finish get Department : "+departmentDto);
    }

    @Override
    public void updateDepartment(DepartmentUpdateRequest request, StreamObserver<DepartmentUpdateResponse> responseObserver) {

        logger.info("Start Update Department : "+request.getDepartmentDto());

        Department department= departmentRepository.findById(request.getDepartmentDto().getId())
                        .orElseThrow(() -> new RuntimeException("Record Not Found"));

        DepartmentDto departmentDto=DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));

        responseObserver.onNext(DepartmentUpdateResponse.newBuilder()
                        .setDepartmentDto(departmentDto)
                .build());
        responseObserver.onCompleted();
        logger.info("finish Update Department : "+departmentDto);


    }

    @Override
    public void getAllDepartment(Empty request, StreamObserver<AllDepartmentSearchResponse> responseObserver) {

        logger.info("Start get all Department : ");

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
                departmentRepository.findAll()
                .forEach(department -> departmentDtoList.add( DepartmentMapper.mapToDepartmentDto(department)));

        responseObserver.onNext(AllDepartmentSearchResponse.newBuilder()
                        .addAllDepartmentDto(departmentDtoList)
                .build());

        responseObserver.onCompleted();

        logger.info("Finish get all Department : "+departmentDtoList.size());

    }

    @Override
    public void deleteDepartment(DepartmentDeleteRequest request, StreamObserver<DepartmentDeleteResponse> responseObserver) {

        logger.info("Start delete Department : "+request.getId());

        Department department=departmentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        departmentRepository.delete(department);
        responseObserver.onNext(DepartmentDeleteResponse.newBuilder()
                        .setStatus(1)
                .build());
        responseObserver.onCompleted();
        logger.info("finish delete Department : "+department);

    }

    @Override
    public void getDepartmentByCode(DepartmentByCodeRequest request, StreamObserver<DepartmentSearchResponse> responseObserver) {


        logger.info("Start get Department by code : "+request.getDepartmentCode());
        DepartmentDto departmentDto =  DepartmentMapper.mapToDepartmentDto(
                departmentRepository.findByDepartmentCodeIgnoreCase(request.getDepartmentCode())
                        .orElseThrow(() -> new RuntimeException("Record Not found")));

        responseObserver.onNext(DepartmentSearchResponse.newBuilder()
                .setDepartmentDto(departmentDto)
                .build());
        responseObserver.onCompleted();

        logger.info("Finish get Department by code: "+departmentDto);
    }
}

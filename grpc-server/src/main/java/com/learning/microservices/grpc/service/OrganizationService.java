package com.learning.microservices.grpc.service;

import com.google.protobuf.Empty;
import com.learning.microservices.grpc.*;
import com.learning.microservices.grpc.entity.Organization;
import com.learning.microservices.grpc.mapper.OrganizationMapper;
import com.learning.microservices.grpc.repository.OrganizationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class OrganizationService extends OrganizationServiceGrpc.OrganizationServiceImplBase {

    Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void saveOrganization(OrganizationSaveRequest request, StreamObserver<OrganizationSaveResponse> responseObserver) {

        logger.info("Start save Organization : "+request.getOrganizationDto());
        Organization organization = OrganizationMapper.mapToOrganization(request.getOrganizationDto());
        OrganizationDto organizationDto = OrganizationMapper.mapToOrganizationDto(organizationRepository.save(organization));

        responseObserver.onNext(OrganizationSaveResponse.newBuilder().
                setOrganizationDto(organizationDto)
                .build());
        responseObserver.onCompleted();
        logger.info("Finish save Organization : "+organizationDto);
    }

    @Override
    public void getOrganization(OrganizationSearchRequest request, StreamObserver<OrganizationSearchResponse> responseObserver) {

        logger.info("Start get Organization : "+request.getId());
        OrganizationDto organizationDto =  OrganizationMapper.mapToOrganizationDto(
                organizationRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("Record Not found")));

        responseObserver.onNext(OrganizationSearchResponse.newBuilder()
                .setOrganizationDto(organizationDto)
                .build());
        responseObserver.onCompleted();

        logger.info("Finish get Organization : "+organizationDto);
    }

    @Override
    public void updateOrganization(OrganizationUpdateRequest request, StreamObserver<OrganizationUpdateResponse> responseObserver) {

        logger.info("Start Update Organization : "+request.getOrganizationDto());

        Organization organization= organizationRepository.findById(request.getOrganizationDto().getId())
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        OrganizationDto organizationDto=OrganizationMapper.mapToOrganizationDto(organizationRepository.save(organization));

        responseObserver.onNext(OrganizationUpdateResponse.newBuilder()
                .setOrganizationDto(organizationDto)
                .build());
        responseObserver.onCompleted();
        logger.info("finish Update Organization : "+organizationDto);
    }

    @Override
    public void getAllOrganization(Empty request, StreamObserver<AllOrganizationSearchResponse> responseObserver) {

        logger.info("Start get all Organization : ");

        List<OrganizationDto> organizationDtoList = new ArrayList<>();
        organizationRepository.findAll()
                .forEach(department -> organizationDtoList.add( OrganizationMapper.mapToOrganizationDto(department)));

        responseObserver.onNext(AllOrganizationSearchResponse.newBuilder()
                .addAllOrganizationDto(organizationDtoList)
                .build());

        responseObserver.onCompleted();

        logger.info("Finish get all Organization : "+organizationDtoList.size());
    }

    @Override
    public void deleteOrganization(OrganizationDeleteRequest request, StreamObserver<OrganizationDeleteResponse> responseObserver) {

        logger.info("Start delete Organization : "+request.getId());

        Organization organization=organizationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        organizationRepository.delete(organization);
        responseObserver.onNext(OrganizationDeleteResponse.newBuilder()
                .setStatus(1)
                .build());
        responseObserver.onCompleted();
        logger.info("finish delete Organization : "+organization);
    }

    @Override
    public void getOrganizationByCode(OrganizationByCodeRequest request, StreamObserver<OrganizationSearchResponse> responseObserver) {

        logger.info("Start get Organization by code: "+request.getOrganizationCode());
        OrganizationDto organizationDto =  OrganizationMapper.mapToOrganizationDto(
                organizationRepository.findByOrganizationCodeIgnoreCase(request.getOrganizationCode())
                        .orElseThrow(() -> new RuntimeException("Record Not found")));

        responseObserver.onNext(OrganizationSearchResponse.newBuilder()
                .setOrganizationDto(organizationDto)
                .build());
        responseObserver.onCompleted();

        logger.info("Finish get Organization by code : "+organizationDto);
    }
}

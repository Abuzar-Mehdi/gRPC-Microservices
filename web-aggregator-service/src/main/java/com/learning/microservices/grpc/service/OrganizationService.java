package com.learning.microservices.grpc.service;


import com.google.protobuf.Empty;
import com.learning.microservices.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @GrpcClient("grpc-server")
    private OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub;

    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        OrganizationSaveRequest organizationSaveRequest = OrganizationSaveRequest.newBuilder()
                .setOrganizationDto(organizationDto)
                .build();

        OrganizationSaveResponse organizationSaveResponse
                =organizationServiceBlockingStub.saveOrganization(organizationSaveRequest);

        return organizationSaveResponse.getOrganizationDto();
    }


    public OrganizationDto getOrganizationById(Long id) {

        OrganizationSearchRequest organizationSearchRequest = OrganizationSearchRequest.newBuilder()
                .setId(id)
                .build();

        OrganizationSearchResponse organizationSearchResponse
                = organizationServiceBlockingStub.getOrganization(organizationSearchRequest);

        return organizationSearchResponse.getOrganizationDto();
    }


    public OrganizationDto findByOrganizationCode(String code) {

        OrganizationByCodeRequest organizationByCodeRequest = OrganizationByCodeRequest.newBuilder()
                .setOrganizationCode(code)
                .build();

        OrganizationSearchResponse organizationSearchResponse
                = organizationServiceBlockingStub.getOrganizationByCode(organizationByCodeRequest);

        return organizationSearchResponse.getOrganizationDto();
    }


    public List<OrganizationDto> getAllOrganization() {

        AllOrganizationSearchResponse allOrganization = organizationServiceBlockingStub.getAllOrganization(Empty.newBuilder().build());
        return allOrganization.getOrganizationDtoList();
    }


    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {

       OrganizationUpdateRequest organizationUpdateRequest
               = OrganizationUpdateRequest.newBuilder()
               .setOrganizationDto(organizationDto)
               .build();

        OrganizationUpdateResponse organizationUpdateResponse
                = organizationServiceBlockingStub.updateOrganization(organizationUpdateRequest);

        return  organizationUpdateResponse.getOrganizationDto();
    }


    public Long deleteOrganization(Long id) {

        OrganizationDeleteRequest organizationDeleteRequest
                = OrganizationDeleteRequest.newBuilder()
                .setId(id)
                .build();

        OrganizationDeleteResponse organizationDeleteResponse
                = organizationServiceBlockingStub.deleteOrganization(organizationDeleteRequest);

        return organizationDeleteResponse.getStatus();
    }
}

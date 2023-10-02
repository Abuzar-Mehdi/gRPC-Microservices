package com.learning.microservices.grpc.mapper;

import com.learning.microservices.grpc.OrganizationDto;
import com.learning.microservices.grpc.entity.Organization;

import java.time.LocalDateTime;

public class OrganizationMapper {

    public static OrganizationDto mapToOrganizationDto(Organization organization){

        OrganizationDto organizationDto = OrganizationDto.newBuilder()
                .setCreateDate(organization.getCreateDate().toString())
                .setOrganizationCode(organization.getOrganizationCode())
                .setOrganizationDescription(organization.getOrganizationDescription())
                .setOrganizationName(organization.getOrganizationName())
                .setId(organization.getId())
                .build();

        return organizationDto;
    }

    public static Organization mapToOrganization(OrganizationDto organizationDto){

        Organization organization = Organization.builder()
                .createDate(LocalDateTime.parse(organizationDto.getCreateDate()))
                .organizationCode(organizationDto.getOrganizationCode())
                .organizationName(organizationDto.getOrganizationName())
                .organizationDescription(organizationDto.getOrganizationDescription())
                .id(organizationDto.getId())
                .build();

        return organization;
    }
}

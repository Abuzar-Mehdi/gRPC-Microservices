package com.learning.microservices.grpc.repository;

import com.learning.microservices.grpc.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Optional<Organization> findByOrganizationCodeIgnoreCase(String organizationCode);

}

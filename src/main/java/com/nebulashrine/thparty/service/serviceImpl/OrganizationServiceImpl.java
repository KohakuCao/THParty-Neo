package com.nebulashrine.thparty.service.serviceImpl;

import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.repository.OrganizationRepo;
import com.nebulashrine.thparty.service.serviceInterface.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }

    @Override
    public Organization queryOrganization(String orgName) {
        Organization org = organizationRepo.findAllByName(orgName);
        if (org == null){
            throw new EntityCannotFoundException("Cannot find this organization");
        }
        return org;
    }
}

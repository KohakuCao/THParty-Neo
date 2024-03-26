package com.nebulashrine.thparty.service.serviceInterface;

import com.nebulashrine.thparty.entity.mysqlEntity.Organization;

public interface OrganizationService {

    Organization queryOrganization(String orgName);
}

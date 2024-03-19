package com.nebulashrine.thparty.repository;

import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends CrudRepository<Organization, Integer> {
}

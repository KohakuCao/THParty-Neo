package com.nebulashrine.thparty.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OrganizationUtils {

    private final OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationUtils(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }

    public ArrayList<Organization> mergeOrganization(ArrayList<Organization> list1,
                                                     ArrayList<Organization> list2){
        ArrayList<Organization> list = new ArrayList<>(list1);
        list.removeAll(list2);
        list.addAll(list1);
        return list;
    }

    public ArrayList<Organization> deserializationOrg(String organizationJSON){
        ArrayList<Organization> organizations = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            organizations = mapper.readValue(organizationJSON, new TypeReference<ArrayList<Organization>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return organizations;
    }

    public String serializationOrg(ArrayList<Organization> organizations){
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(organizations);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}

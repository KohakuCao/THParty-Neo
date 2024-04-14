package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.utils.OrganizationUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.service.serviceInterface.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OrganizationController", description = "社团相关Controller")
@RestController()
@RequestMapping("/api/org")
public class OrganizationController {

    private final OrganizationUtils organizationUtils;

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationUtils organizationUtils, OrganizationService organizationService) {
        this.organizationUtils = organizationUtils;
        this.organizationService = organizationService;
    }

    @RequestMapping("/{organization}")
    @Operation(summary = "findOrganization", description = "查找社团")
    @Parameter(name = "organizationName", description = "社团名称")
    public Result findOrganization(@PathVariable("organization") String orgName){
        Organization organization = organizationService.queryOrganization(orgName);
        organization.setId(2147483647);
        return Result.succeed(organization);
    }
}

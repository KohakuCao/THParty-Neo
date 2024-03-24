package com.nebulashrine.thparty.common.utils;

import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.entity.vo.PartyVO;
import com.nebulashrine.thparty.repository.OrganizationRepo;
import com.nebulashrine.thparty.repository.PartyRepo;
import com.nebulashrine.thparty.repository.UserRepo;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class PartyUtils {

    private final UserUtils userUtils;

    private final OrganizationUtils organizationUtils;

    private final PartyRepo partyRepo;

    private final UserRepo userRepo;

    private final OrganizationRepo organizationRepo;


    @Autowired
    public PartyUtils(UserUtils userUtils, PartyRepo partyRepo, UserRepo userRepo,
                      OrganizationRepo organizationRepo, OrganizationUtils organizationUtils) {
        this.userUtils = userUtils;
        this.partyRepo = partyRepo;
        this.userRepo = userRepo;
        this.organizationRepo = organizationRepo;
        this.organizationUtils = organizationUtils;
    }

    public Party generateParty(User user, PartyVO partyVO){
        Party party = new Party();
        party.setTitle(partyVO.getTitle());
        party.setSubTitle(partyVO.getSubTitle());
        party.setInformation(partyVO.getInformation());
        party.setStartTime(partyVO.getStartTime());
        party.setEndTime(partyVO.getEndTime());
        party.setDomain(partyVO.getDomain());
        party.setLocation(partyVO.getLocation());

        String[] leadersName = partyVO.getLeadersName();
        ArrayList<User> leaders = new ArrayList<>();
        HashSet<String> leadersSet = new HashSet<>(Arrays.asList(leadersName));
        leadersSet.forEach(username -> {
            User userByUsername = userRepo.findUserByUsername(username);
            if (userByUsername != null){
                leaders.add(userByUsername);
            }
        });

        String[] staffsName = partyVO.getStaffsName();
        ArrayList<User> staffs = new ArrayList<>();
        HashSet<String> staffsSet = new HashSet<>(Arrays.asList(staffsName));
        staffsSet.forEach(username -> {
            User userByUsername = userRepo.findUserByUsername(username);
            if (userByUsername != null){
                staffs.add(userByUsername);
            }
        });

        String[] organizationsName = partyVO.getOrganizationsName();
        ArrayList<Organization> organizations = new ArrayList<>();
        HashSet<String> organizationSet = new HashSet<>(Arrays.asList(organizationsName));
        organizationSet.forEach(org -> {
            Organization orgByName = organizationRepo.findAllByName(org);
            if (orgByName != null){
                organizations.add(orgByName);
            }
        });

        party.setLeaders(userUtils.serializationUser(leaders));
        party.setStaffs(userUtils.serializationUser(staffs));
        party.setOrganizations(organizationUtils.serializationOrg(organizations));

        return partyRepo.save(party);
    }
}

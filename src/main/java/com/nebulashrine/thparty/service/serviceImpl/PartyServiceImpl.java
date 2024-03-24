package com.nebulashrine.thparty.service.serviceImpl;

import com.nebulashrine.thparty.common.exceptions.AccessDeniedException;
import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.common.exceptions.MultipleEntitiesException;
import com.nebulashrine.thparty.common.utils.OrganizationUtils;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.repository.PartyRepo;
import com.nebulashrine.thparty.service.serviceInterface.PartyService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class PartyServiceImpl implements PartyService {

    private final UserUtils userUtils;

    private final OrganizationUtils organizationUtils;

    private final PartyRepo partyRepo;

    @Autowired
    public PartyServiceImpl(UserUtils userUtils, PartyRepo partyRepo, OrganizationUtils organizationUtils) {
        this.userUtils = userUtils;
        this.partyRepo = partyRepo;
        this.organizationUtils = organizationUtils;
    }

    @Override
    public Party createParty(Party party) {
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party modifyParty(Party party){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }

        int id = party.getId();
        Party queryParty = partyRepo.queryAllById(id);
        if (queryParty == null){
            throw new EntityCannotFoundException("Party Cannot Found!");
        }
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party deleteParty(Party party){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        party = partyRepo.deleteParty(party.getId());
        if (party == null){
            throw new EntityCannotFoundException("Party Cannot Found!");
        }
        return party;
    }

    @Transactional
    @Override
    public Party addStaffs(Party party, ArrayList<User> staffs){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String staff = party.getStaffs();
        ArrayList<User> users = new ArrayList<>();
        if (null != staff && !staff.isEmpty()){
            users = userUtils.deserializationUser(staff);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, staffs);
        return partyRepo.modifyStaffs(party.getId(), userUtils.serializationUser(list));
    }

    @Transactional
    @Override
    public Party addParticipants(Party party, ArrayList<User> participants){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String participant = party.getParticipants();
        ArrayList<User> users = new ArrayList<>();
        if (null != participant && !participant.isEmpty()){
            users = userUtils.deserializationUser(participant);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, participants);
        return partyRepo.modifyParticipants(party.getId(), userUtils.serializationUser(list));
    }

    @Transactional
    @Override
    public Party addOrganizations(Party party, ArrayList<Organization> organizations){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String organization = party.getOrganizations();
        ArrayList<Organization> org = new ArrayList<>();
        if (null != organization && !organization.isEmpty()){
            org = organizationUtils.deserializationOrg(organization);
        }
        ArrayList<Organization> list = organizationUtils.mergeOrganization(org, organizations);
        return partyRepo.modifyOrganizations(party.getId(), organizationUtils.serializationOrg(list));
    }

    @Transactional
    @Override
    public Party addLeaders(Party party, ArrayList<User> leaders){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String leader = party.getLeaders();
        ArrayList<User> users = new ArrayList<>();
        if (null != leader && !leader.isEmpty()){
            users = userUtils.deserializationUser(leader);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, leaders);
        return partyRepo.modifyLeaders(party.getId(), userUtils.serializationUser(list));
    }

    @Transactional
    @Override
    public Party removeStaff(Party party, User staff){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String staffs = party.getStaffs();
        ArrayList<User> users = new ArrayList<>();
        if (null != staffs && !staffs.isEmpty()){
            users = userUtils.deserializationUser(staffs);
        }
        if (!users.contains(staff)){
            throw new EntityCannotFoundException("This user not in this group");
        }
        users.remove(staff);
        String json = userUtils.serializationUser(users);
        party.setStaffs(json);
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party removeLeader(Party party, User leader){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String leaders = party.getLeaders();
        ArrayList<User> users = new ArrayList<>();
        if (null != leaders && !leaders.isEmpty()){
            users = userUtils.deserializationUser(leaders);
        }
        if (!users.contains(leader)){
            throw new EntityCannotFoundException("This user not in this group");
        }
        users.remove(leader);
        String json = userUtils.serializationUser(users);
        party.setLeaders(json);
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party removeOrganization(Party party, Organization organization){
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String org = party.getOrganizations();
        ArrayList<Organization> organizations = new ArrayList<>();
        if (null != org && !org.isEmpty()){
            organizations = organizationUtils.deserializationOrg(org);
        }
        if (!organizations.contains(organization)){
            throw new EntityCannotFoundException("This organization not in this party");
        }
        organizations.remove(organization);
        String json = organizationUtils.serializationOrg(organizations);
        party.setOrganizations(json);
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party joinParty(Party party, User participant){
        String user = party.getParticipants();
        ArrayList<User> users = new ArrayList<>();
        if (null != user && !user.isEmpty()){
            users = userUtils.deserializationUser(user);
        }
        if (users.contains(participant)){
            throw new MultipleEntitiesException("you have already joint this party");
        }
        users.add(participant);
        String json = userUtils.serializationUser(users);
        party.setParticipants(json);
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party leaveParty(Party party, User participant){
        String participants = party.getParticipants();
        ArrayList<User> users = new ArrayList<>();
        if (null != participant && !participants.isEmpty()){
            users = userUtils.deserializationUser(participants);
        }
        if (!users.contains(participant)){
            throw new EntityCannotFoundException("you are not in this party!");
        }
        users.remove(participant);
        String json = userUtils.serializationUser(users);
        party.setParticipants(json);
        return partyRepo.save(party);
    }

    @Override
    public @Nullable Party queryParty(String path) {
        return partyRepo.queryAllByPath(path);
    }

    @Override
    public @Nullable Party queryParty(int id) {
        return partyRepo.queryAllById(id);
    }


    private boolean hasAuthorities(Party party) {
        User user = userUtils.getUser();
        String leaders = party.getLeaders();
        if (user == null || leaders == null){
            return false;
        }
        int id = user.getId();
        ArrayList<User> users = userUtils.deserializationUser(leaders);
        if (users.isEmpty()){
            return false;
        }
        for (User u : users) {
            if (u.getId() == id){
                return true;
            }
        }
        return false;
    }
}

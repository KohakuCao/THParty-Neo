package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.common.exceptions.AccessDeniedException;
import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.repository.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class PartyServiceImpl implements PartyService{

    private final UserUtils userUtils;

    private final PartyRepo partyRepo;

    @Autowired
    public PartyServiceImpl(UserUtils userUtils, PartyRepo partyRepo) {
        this.userUtils = userUtils;
        this.partyRepo = partyRepo;
    }

    @Override
    public Party createParty(Party party) {
        return partyRepo.save(party);
    }

    @Transactional
    @Override
    public Party modifyParty(Party party) throws AccessDeniedException, EntityCannotFoundException {
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
    public Party deleteParty(Party party) throws AccessDeniedException, EntityCannotFoundException{
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

    @Override
    public Party addStaffs(Party party, ArrayList<User> staffs) throws AccessDeniedException, EntityCannotFoundException{
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String staff = party.getStaffs();
        ArrayList<User> users = null;
        if (null != staff && !staff.isEmpty()){
            users = userUtils.deserializationUser(staff);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, staffs);
        return partyRepo.modifyStaffs(party.getId(), userUtils.serializationUser(list));
    }

    @Override
    public Party addParticipants(Party party, ArrayList<User> participants) throws AccessDeniedException, EntityCannotFoundException{
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String participant = party.getParticipants();
        ArrayList<User> users = null;
        if (null != participant && !participant.isEmpty()){
            users = userUtils.deserializationUser(participant);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, participants);
        return partyRepo.modifyParticipants(party.getId(), userUtils.serializationUser(list));
    }

    @Override
    public Party addOrganizations(Party party, ArrayList<Organization> organizations) {
        return null;
    }

    @Override
    public Party addLeaders(Party party, ArrayList<User> leaders) throws AccessDeniedException, EntityCannotFoundException{
        boolean flag = hasAuthorities(party);
        if (!flag){
            throw new AccessDeniedException("Party Modified Access Denied");
        }
        String leader = party.getLeaders();
        ArrayList<User> users = null;
        if (null != leader && !leader.isEmpty()){
            users = userUtils.deserializationUser(leader);
        }
        ArrayList<User> list = userUtils.mergeUserList(users, leaders);
        return partyRepo.modifyLeaders(party.getId(), userUtils.serializationUser(list));
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

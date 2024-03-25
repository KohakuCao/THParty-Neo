package com.nebulashrine.thparty.service.serviceInterface;

import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;

import java.util.ArrayList;

public interface PartyService {
    Party createParty(Party party);

    Party modifyParty(Party party);

    Party deleteParty(Party party);

    Party addStaffs(Party party, ArrayList<User> staffs);

    Party addParticipants(Party party, ArrayList<User> participants);

    Party addOrganizations(Party party, ArrayList<Organization> organizations);

    Party addLeaders(Party party, ArrayList<User> leaders);

    Party removeStaff(Party party, User staff);


    Party removeLeader(Party party, User leader);

    Party removeOrganization(Party party, Organization organization);

    Party joinParty(Party party, User participant);

    Party leaveParty(Party party, User participant);

    Party queryParty(String path);

    Party queryParty(int id);

    ArrayList<Party> queryAllValidParty();
}

package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.common.exceptions.AccessDeniedException;
import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.entity.mysqlEntity.Organization;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;

import java.util.ArrayList;

public interface PartyService {
    Party createParty(Party party);

    Party modifyParty(Party party)  throws AccessDeniedException, EntityCannotFoundException;

    Party deleteParty(Party party) throws AccessDeniedException, EntityCannotFoundException;

    Party addStaffs(Party party, ArrayList<User> staffs) throws AccessDeniedException, EntityCannotFoundException;

    Party addParticipants(Party party, ArrayList<User> participants) throws AccessDeniedException, EntityCannotFoundException;

    Party addOrganizations(Party party, ArrayList<Organization> organizations) throws AccessDeniedException, EntityCannotFoundException;

    Party addLeaders(Party party, ArrayList<User> leaders) throws AccessDeniedException, EntityCannotFoundException;

}

package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.entity.mysqlEntity.Party;

public interface PartyService {
    Party createParty(Party party);

    Party modifyParty(Party party);

    Party deleteParty(int id);


}

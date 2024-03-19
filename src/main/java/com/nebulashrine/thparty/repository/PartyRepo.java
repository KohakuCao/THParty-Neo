package com.nebulashrine.thparty.repository;

import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepo extends CrudRepository<Party, Integer> {

    @Modifying
    @Query("update Party party set party.is_deleted = true where party.id = :id")
    Party deleteParty(@Param("id") int partyId);

    @Modifying
    @Query("update Party party set party.is_deleted = false where party.id = :id")
    Party recoverParty(@Param("id") int partyId);

    @Modifying
    @Query("update Party party set party.staff = :staffs where party.id = :id")
    Party modifyStaffs(@Param("id") int partyId, @Param("staffs") String newStaffs);

    @Modifying
    @Query("update Party party set party.organizations = :newOrganizations where party.id = :id")
    Party modifyOrganizations(@Param("id") int partyId, @Param("staffs") String newOrganizations);

    @Modifying
    @Query("update Party party set party.leaders = :newLeaders where party.id = :id")
    Party modifyLeaders(@Param("id") int partyId, @Param("staffs") String newLeaders);

    String queryLeadersById(int id);
}

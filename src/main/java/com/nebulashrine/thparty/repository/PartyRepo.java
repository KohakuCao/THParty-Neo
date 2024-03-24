package com.nebulashrine.thparty.repository;

import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepo extends CrudRepository<Party, Integer>, PagingAndSortingRepository<Party, Integer> {

    @Modifying
    @Query("update Party party set party.isDeleted = true where party.id = :id and party.isActive = true")
    Party deleteParty(@Param("id") int partyId);

    @Modifying
    @Query("update Party party set party.isDeleted = false where party.id = :id and party.isActive = true")
    Party recoverParty(@Param("id") int partyId);

    @Modifying
    @Query("update Party party set party.staffs = :staffs where party.id = :id and party.isActive = true and party.isDeleted = false ")
    Party modifyStaffs(@Param("id") int partyId, @Param("staffs") String newStaffs);

    @Modifying
    @Query("update Party party set party.organizations = :newOrganizations where party.id = :id and party.isActive = true and party.isDeleted = false ")
    Party modifyOrganizations(@Param("id") int partyId, @Param("newOrganizations") String newOrganizations);

    @Modifying
    @Query("update Party party set party.leaders = :newLeaders where party.id = :id and party.isActive = true and party.isDeleted = false ")
    Party modifyLeaders(@Param("id") int partyId, @Param("newLeaders") String newLeaders);

    @Modifying
    @Query("update Party party set party.participants = :newParticipants where party.id = :id and party.isActive = true and party.isDeleted = false ")
    Party modifyParticipants(@Param("id") int partyId, @Param("newParticipants") String newParticipants);

    String queryLeadersById(int id);

    Party queryAllById(int id);

    Party queryAllByPath(String path);

}

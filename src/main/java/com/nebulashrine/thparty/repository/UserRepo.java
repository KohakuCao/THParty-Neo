package com.nebulashrine.thparty.repository;

import com.nebulashrine.thparty.entity.mysqlEntity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User findUserByUsername(String username);

    @Modifying
    @Query("update User user set user.parties = :parties where user.username = :username and user.isDeleted = false " +
            "and user.isBanned = false and user.isAuthorized = true ")
    User modifyParty(@Param("username") String username, @Param("parties") String parties);

}

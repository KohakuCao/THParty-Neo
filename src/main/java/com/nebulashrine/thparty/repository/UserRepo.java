package com.nebulashrine.thparty.repository;

import com.nebulashrine.thparty.entity.mysqlEntity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User findUserByUsername(String username);
}

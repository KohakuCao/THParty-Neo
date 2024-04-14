package com.nebulashrine.thparty.repository.games;

import com.nebulashrine.thparty.entity.mysqlEntity.games.AnnoyingUFO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnnoyingUFORepo extends CrudRepository<AnnoyingUFO, Integer> {

    @Query(value = "select * from thparty_neo.annoyingufo order by RAND() limit 1",
            nativeQuery = true)
    AnnoyingUFO getRandom();
}

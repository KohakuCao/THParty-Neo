package com.nebulashrine.thparty.entity.mysqlEntity.games;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnoyingUFO {

    @Id
    private int id;

    private String name;

    private String words;

    private int createdBy;

    private Date updateAt;

    private boolean isActivate = true;

}

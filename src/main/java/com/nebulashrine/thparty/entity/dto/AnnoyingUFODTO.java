package com.nebulashrine.thparty.entity.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebulashrine.thparty.entity.mysqlEntity.games.AnnoyingUFO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnnoyingUFODTO {

    public AnnoyingUFODTO(AnnoyingUFO annoyingUFO) throws Exception{
        this.id = annoyingUFO.getId();
        this.name = annoyingUFO.getName();
        this.createdBy = annoyingUFO.getCreatedBy();
        this.updateAt = annoyingUFO.getUpdateAt();
        this.isActivate = annoyingUFO.isActivate();

        ObjectMapper mapper = new ObjectMapper();
        words = mapper.readValue(annoyingUFO.getWords(), new TypeReference<ArrayList<String>>() {
        });
    }


    @Id
    private int id;

    private String name;

    private ArrayList<String> words;

    private int createdBy;

    private Date updateAt;

    private boolean isActivate;
}

package com.nebulashrine.thparty.entity.mysqlEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String title;

    public String subTitle;

    public String information;

    public Date startTime;

    public Date endTime;

    public int viewers;

    public String domain;

    public String leaders;

    public String staff;

    public String participants;

    public String organizations;

    public boolean isActive;

    public String location;

    public boolean is_deleted;
}

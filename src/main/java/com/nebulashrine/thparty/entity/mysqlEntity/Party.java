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

    private String title;

    private String subTitle;

    private String information;

    private Date startTime;

    private Date endTime;

    private int viewers;

    private String domain = id + "";

    private String leaders;

    private String staffs;

    private String participants;

    private String organizations;

    private boolean isActive;

    private String location;

    private boolean isDeleted;
}

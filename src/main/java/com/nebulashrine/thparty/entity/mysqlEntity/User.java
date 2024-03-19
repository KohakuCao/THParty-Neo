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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String uuid;

    /**
     * TODO: 设置字段唯一
     */
    private String username;

    private String password;

    private String gender;

    private String email;

    private String phoneNumber;

    private String authority;

    private Date registerDate;

    private Date lastLoginDate;

    private String lastLoginIp;

    private Date birthday;

    private String avatarURL;

    private String signature;

    private String profile;

    private String personalWebsite;

    private String location;

    private String qqNumber;

    private boolean isDeleted;

    private boolean isBanned;

    private boolean isAuthorized;

    private String rank;
}

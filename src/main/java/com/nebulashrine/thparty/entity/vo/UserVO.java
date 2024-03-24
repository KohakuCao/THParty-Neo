package com.nebulashrine.thparty.entity.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {

    @NotNull
    private int id;

    @NotNull
    private String uuid;

    @NotNull
    @Size(min = 3, max = 16, message = "id长度不合法")
    private String username;

    private String password;

    private String gender;

    @Email(message = "邮箱格式不合法")
    private String email;

    private String phoneNumber;

    private Date birthday;

    private String signature;

    private String profile;

    private String personalWebsite;

    private String location;

    private String qqNumber;


}

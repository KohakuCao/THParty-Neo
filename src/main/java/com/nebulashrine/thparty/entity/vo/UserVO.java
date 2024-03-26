package com.nebulashrine.thparty.entity.vo;

import io.swagger.v3.oas.annotations.Parameter;
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

    @Parameter(name = "id", description = "id")
    @NotNull
    private int id;

    @NotNull
    private String uuid;

    @Parameter(name = "username", description = "用户名(唯一)")
    @NotNull
    @Size(min = 3, max = 16, message = "id长度不合法")
    private String username;

    @Parameter(name = "password", description = "密码")
    private String password;

    @Parameter(name = "gender", description = "性别")
    private String gender;

    @Parameter(name = "email", description = "邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;

    @Parameter(name = "phone", description = "电话")
    private String phoneNumber;

    @Parameter(name = "birthday", description = "生日")
    private Date birthday;

    @Parameter(name = "signature", description = "签名")
    private String signature;

    @Parameter(name = "profile", description = "个人简介")
    private String profile;

    @Parameter(name = "web", description = "个人网站")
    private String personalWebsite;

    @Parameter(name = "location", description = "位置")
    private String location;

    @Parameter(name = "qq", description = "qq")
    private String qqNumber;


}

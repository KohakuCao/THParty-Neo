package com.nebulashrine.thparty.entity.dto;

import com.nebulashrine.thparty.entity.mysqlEntity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserDetails {


    public UserDTO(User user){
        this.authority = user.getAuthority();
        this.avatarURL = user.getAvatarURL();
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
        this.isAuthorized = user.isAuthorized();
        this.isBanned = user.isBanned();
        this.isDeleted = user.isDeleted();
        this.lastLoginDate = user.getLastLoginDate();
        this.lastLoginIp = user.getLastLoginIp();
        this.location = user.getLocation();
        this.password = user.getPassword();
        this.personalWebsite = user.getPersonalWebsite();
        this.qqNumber = user.getQqNumber();
        this.profile = user.getProfile();
        this.rank = user.getRank();
        this.phoneNumber = user.getPhoneNumber();
        this.signature = user.getSignature();
        this.uuid = user.getUuid();
        this.registerDate = user.getRegisterDate();
    }


    private int id;

    private String uuid;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

package com.nebulashrine.thparty.service;

public interface AuthService {

    String register(String username, String jti);

    String loginViaTHP(String username, String ip);
}

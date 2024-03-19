package com.nebulashrine.thparty.common.exceptions;

/**
 * 自定义的JwtAuthException, 当JwtAuthFilter验证失败时抛出s
 */
public class JwtAuthException extends Exception{

    public JwtAuthException(){}

    public JwtAuthException(String msg){
        super(msg);
    }
}


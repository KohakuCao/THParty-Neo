package com.nebulashrine.thparty.common.exceptions;

public class UserNotLoginException extends RuntimeException{
    public UserNotLoginException() {
    }

    public UserNotLoginException(String message) {
        super(message);
    }
}

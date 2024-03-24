package com.nebulashrine.thparty.common.exceptions;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException() {
    }
}

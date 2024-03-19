package com.nebulashrine.thparty.common.exceptions;

public class AccessDeniedException extends Exception{

    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException() {
    }
}

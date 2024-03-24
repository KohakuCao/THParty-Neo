package com.nebulashrine.thparty.common.exceptions;

public class EntityCannotFoundException extends RuntimeException{

    public EntityCannotFoundException() {
    }

    public EntityCannotFoundException(String message) {
        super(message);
    }
}

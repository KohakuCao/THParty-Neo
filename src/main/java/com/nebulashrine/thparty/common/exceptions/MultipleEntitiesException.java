package com.nebulashrine.thparty.common.exceptions;

public class MultipleEntitiesException extends RuntimeException{
    public MultipleEntitiesException() {
    }

    public MultipleEntitiesException(String message) {
        super(message);
    }
}

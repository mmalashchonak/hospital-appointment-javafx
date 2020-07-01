package com.stormnet.dentapp.dao.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super("Object not found!");
    }
}

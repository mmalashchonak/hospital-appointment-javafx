package com.stormnet.dentapp.dao.exception;

public class ObjectAlreadyStoredException extends RuntimeException {
    public ObjectAlreadyStoredException() {
        super("Object is Already Stored!");
    }
}

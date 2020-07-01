package com.stormnet.dentapp.dao.exception;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException() {
        super("This ID does not exist!");
    }
}

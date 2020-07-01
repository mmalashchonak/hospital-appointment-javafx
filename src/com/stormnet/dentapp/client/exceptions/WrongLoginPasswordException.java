package com.stormnet.dentapp.client.exceptions;

public class WrongLoginPasswordException extends RuntimeException {

    public WrongLoginPasswordException() {
        super("Wrong Login or Password!");
    }
}

package com.stormnet.dentapp.dao.exception;

import com.stormnet.dentapp.dao.factory.DaoTypes;

public class UnknownDaoTypeException extends RuntimeException {

    public UnknownDaoTypeException(DaoTypes type) {
        super("Can not find DAO Factory for  " + type + "!");
    }
}

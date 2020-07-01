package com.stormnet.dentapp.dao;

import com.stormnet.dentapp.bo.Person;

public interface PersonDao<T extends Person> extends BaseDao<T> {


    T loadPersonByLoginAndPassword(String login, String password);
}

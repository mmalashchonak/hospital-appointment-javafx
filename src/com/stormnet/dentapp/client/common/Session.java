package com.stormnet.dentapp.client.common;

public class Session {

    private int personId;

    private static final Session session = new Session();

    private Session() {
    }

    public static Session getInstance () {
        return session;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}

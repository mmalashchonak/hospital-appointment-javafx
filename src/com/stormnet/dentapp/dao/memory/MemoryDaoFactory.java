package com.stormnet.dentapp.dao.memory;

import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.TicketDao;
import com.stormnet.dentapp.dao.factory.DaoFactory;

public class MemoryDaoFactory extends DaoFactory {


    @Override
    public TicketDao getTicketDao() {
        return new MemoryTicketDao();
    }

    @Override
    public PersonDao getClientDao() {
        return null;
    }

    @Override
    public PersonDao getDoctorDao() {
        return null;
    }

    @Override
    public PersonDao getAdminDao() {
        return null;
    }

//    @Override
//    public PersonDao getPersonDao() {
//        return new MemoryPersonDao();
//    }
}

package com.stormnet.dentapp.dao.factory;


import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.TicketDao;

import com.stormnet.dentapp.dao.exception.UnknownDaoTypeException;
import com.stormnet.dentapp.dao.memory.MemoryDaoFactory;
import com.stormnet.dentapp.dao.xml.XmlDaoFactory;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;

public abstract class DaoFactory {

    public abstract TicketDao getTicketDao();

    public abstract PersonDao<Client> getClientDao();

    public abstract PersonDao<Doctor> getDoctorDao();

    public abstract PersonDao<Admin> getAdminDao();

    public static DaoFactory getFactory() {
        return getFactory(DaoTypes.XmlDao);
    }

    public static DaoFactory getFactory(DaoTypes type) {
        switch (type) {
            case MemoryDao: {
                return new MemoryDaoFactory();
            }

            case XmlDao: {
                return new XmlDaoFactory();
            }
        }

        throw new UnknownDaoTypeException(type);
    }
}

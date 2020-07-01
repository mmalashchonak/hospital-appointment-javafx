package com.stormnet.dentapp.dao.xml;


import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.TicketDao;
import com.stormnet.dentapp.dao.factory.DaoFactory;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;

public class XmlDaoFactory extends DaoFactory {

    @Override
    public TicketDao getTicketDao() {
        return new XmlTicketDao();
    }

    @Override
    public PersonDao<Client> getClientDao() {
        return new XmlClientDao();
    }

    @Override
    public PersonDao<Doctor> getDoctorDao() {
        return new XmlDoctorDao();
    }

    @Override
    public PersonDao<Admin> getAdminDao() {
        return new XmlAdminDao();
    }
}

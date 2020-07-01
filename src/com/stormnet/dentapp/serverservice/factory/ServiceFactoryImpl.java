package com.stormnet.dentapp.serverservice.factory;

import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.TicketService;
import com.stormnet.dentapp.serverservice.impl.AdminServiceImpl;
import com.stormnet.dentapp.serverservice.impl.ClientServiceImpl;
import com.stormnet.dentapp.serverservice.impl.DoctorServiceImpl;
import com.stormnet.dentapp.serverservice.impl.TicketServiceImpl;

public class ServiceFactoryImpl extends ServiceFactory {

     @Override
    public TicketService getTicketService() {
        return new TicketServiceImpl();
    }

    @Override
    public PersonService<Client> getClientService() {
        return new ClientServiceImpl();
    }

    @Override
    public PersonService<Doctor> getDoctorService() {
        return new DoctorServiceImpl();
    }

    @Override
    public PersonService<Admin> getAdminService() {
        return new AdminServiceImpl();
    }
}

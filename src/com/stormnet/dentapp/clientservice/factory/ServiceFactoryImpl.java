package com.stormnet.dentapp.clientservice.factory;

import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.impl.AdminServiceImpl;
import com.stormnet.dentapp.clientservice.impl.ClientServiceImpl;
import com.stormnet.dentapp.clientservice.impl.DoctorServiceImpl;
import com.stormnet.dentapp.clientservice.impl.TicketServiceImpl;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;

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

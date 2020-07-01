package com.stormnet.dentapp.serverservice.factory;

import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.TicketService;

public abstract class ServiceFactory {

    public abstract TicketService getTicketService();

    public abstract PersonService<Client> getClientService();

    public abstract PersonService<Doctor> getDoctorService();

    public abstract PersonService<Admin> getAdminService();

    public static ServiceFactory getServiceFactory(){
        return new ServiceFactoryImpl();
    }

}

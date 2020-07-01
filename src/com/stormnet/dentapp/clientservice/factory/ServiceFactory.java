package com.stormnet.dentapp.clientservice.factory;

import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;

public abstract class ServiceFactory {

    public abstract TicketService getTicketService();

    public abstract PersonService<Client> getClientService();

    public abstract PersonService<Doctor> getDoctorService();

    public abstract PersonService<Admin> getAdminService();

    public static ServiceFactory getServiceFactory(){
        return new ServiceFactoryImpl();
    }

}

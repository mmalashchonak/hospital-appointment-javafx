package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

import java.util.List;

public class GetAllAdminsCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        PersonService<Admin> personService = ServiceFactory.getServiceFactory().getAdminService();
        List<Admin> persons = personService.loadAll();

        response.getJsonWriter().key("response-bo");
        response.getJsonWriter().array();

        for (Admin person : persons) {

            response.getJsonWriter().object();
            response.getJsonWriter().key("id").value(person.getId().toString());
            response.getJsonWriter().key("lastName").value(person.getLastName());
            response.getJsonWriter().key("firstName").value(person.getFirstName());
            response.getJsonWriter().key("login").value(person.getLogin());
            response.getJsonWriter().key("password").value(person.getPassword());
            response.getJsonWriter().endObject();
        }

        response.getJsonWriter().endArray();
        response.setResponseCode(ResponseCode.OkCode);
    }
}
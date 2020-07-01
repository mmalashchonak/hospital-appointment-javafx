package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

import java.util.List;

public class GetAllDoctorsCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        PersonService<Doctor> personService = ServiceFactory.getServiceFactory().getDoctorService();
        List<Doctor> persons = personService.loadAll();

        response.getJsonWriter().key("response-bo");
        response.getJsonWriter().array();

        for (Doctor person : persons) {

            response.getJsonWriter().object();
            response.getJsonWriter().key("id").value(person.getId().toString());
            response.getJsonWriter().key("lastName").value(person.getLastName());
            response.getJsonWriter().key("firstName").value(person.getFirstName());
            response.getJsonWriter().key("login").value(person.getLogin());
            response.getJsonWriter().key("password").value(person.getPassword());
            response.getJsonWriter().key("cabinet").value(person.getCabinet());
            response.getJsonWriter().endObject();
        }

        response.getJsonWriter().endArray();
        response.setResponseCode(ResponseCode.OkCode);
    }
}
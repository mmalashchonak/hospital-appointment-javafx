package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

public class GetClientByLoginPasswordCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        String loginStr = (String) request.getParameter("login");
        String passwordStr = (String) request.getParameter("password");


        if (loginStr.equals("") || passwordStr.equals("")) {
            System.out.println("Null login or password received");
            response.setResponseCode(ResponseCode.NotFoundCode);
            return;
        }

        PersonService<Client> personService = ServiceFactory.getServiceFactory().getClientService();
        Client person = personService.loadPersonByLoginAndPassword(loginStr, passwordStr);

        if (person == null) {
            System.out.println("Object not found");
            response.setResponseCode(ResponseCode.NotFoundCode);
            return;
        }

        response.getJsonWriter().key("response-bo");
        response.getJsonWriter().array();
        response.getJsonWriter().object();
        response.getJsonWriter().key("id").value(person.getId().toString());
        response.getJsonWriter().key("lastName").value(person.getLastName());
        response.getJsonWriter().key("firstName").value(person.getFirstName());
        response.getJsonWriter().key("login").value(person.getLogin());
        response.getJsonWriter().key("password").value(person.getPassword());
        response.getJsonWriter().endObject();
        response.getJsonWriter().endArray();

        response.setResponseCode(ResponseCode.OkCode);
    }
}

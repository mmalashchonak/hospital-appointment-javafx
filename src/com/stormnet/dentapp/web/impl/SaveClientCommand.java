package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

public class SaveClientCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        String lastNameStr = (String) request.getParameter("lastName");

        String firstNameStr = (String) request.getParameter("firstName");

        String loginStr = (String) request.getParameter("login");

        String passwordStr = (String) request.getParameter("password");

        Client person = new Client();
        person.setFirstName(firstNameStr);
        person.setLastName(lastNameStr);
        person.setLogin(loginStr);
        person.setPassword(passwordStr);

        PersonService<Client> personService = ServiceFactory.getServiceFactory().getClientService();
        personService.save(person);

        response.setResponseCode(ResponseCode.OkCode);
    }
}

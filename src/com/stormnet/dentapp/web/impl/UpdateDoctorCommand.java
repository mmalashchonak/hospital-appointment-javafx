package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.serverservice.PersonService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

public class UpdateDoctorCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        String idStr = (String) request.getParameter("id");
        Integer id = Integer.valueOf(idStr);

        if (idStr.equals("")) {
            System.out.println("Null id received");
            response.setResponseCode(ResponseCode.NotFoundCode);
            return;
        }

        String lastNameStr = (String) request.getParameter("lastName");

        String firstNameStr = (String) request.getParameter("firstName");

        String loginStr = (String) request.getParameter("login");

        String passwordStr = (String) request.getParameter("password");

        String cabinetStr = (String) request.getParameter("cabinet");

        Doctor person = new Doctor();
        person.setId(id);
        person.setFirstName(firstNameStr);
        person.setLastName(lastNameStr);
        person.setLogin(loginStr);
        person.setPassword(passwordStr);
        person.setCabinet(cabinetStr);

        PersonService<Doctor> personService = ServiceFactory.getServiceFactory().getDoctorService();
        personService.update(person);

        response.setResponseCode(ResponseCode.OkCode);
    }
}

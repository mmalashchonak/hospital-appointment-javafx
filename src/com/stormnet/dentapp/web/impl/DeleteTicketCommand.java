package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.serverservice.TicketService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

public class DeleteTicketCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        String idStr = (String) request.getParameter("id");
        Integer id = Integer.valueOf(idStr);

        if (idStr.equals("")) {
            System.out.println("Null id received");
            response.setResponseCode(ResponseCode.NotFoundCode);
            return;
        }

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        ticketService.delete(id);

        response.setResponseCode(ResponseCode.OkCode);
    }
}

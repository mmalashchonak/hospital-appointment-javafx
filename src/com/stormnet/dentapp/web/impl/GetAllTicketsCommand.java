package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.serverservice.TicketService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

import java.util.List;

public class GetAllTicketsCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        List<Ticket> tickets = ticketService.loadAll();

        response.getJsonWriter().key("response-bo");
        response.getJsonWriter().array();

        for (Ticket ticket : tickets) {

            response.getJsonWriter().object();
            response.getJsonWriter().key("id").value(ticket.getId().toString());
            response.getJsonWriter().key("clientId").value(ticket.getClientId().toString());
            response.getJsonWriter().key("isFinished").value(ticket.getFinished().toString());
            response.getJsonWriter().key("doctorLastName").value(ticket.getDoctorLastName());
            response.getJsonWriter().key("doctorFirstName").value(ticket.getDoctorFirstName());
            response.getJsonWriter().key("clientLastName").value(ticket.getClientLastName());
            response.getJsonWriter().key("clientFirstName").value(ticket.getClientFirstName());
            response.getJsonWriter().key("doctorId").value(ticket.getDoctorId().toString());
            response.getJsonWriter().key("date").value(ticket.getDate().toString());
            response.getJsonWriter().key("time").value(ticket.getTime());
            response.getJsonWriter().key("cabinet").value(ticket.getCabinet());
            response.getJsonWriter().key("rating").value(ticket.getRating());
            response.getJsonWriter().key("clientComment").value(ticket.getClientComment());
            response.getJsonWriter().key("doctorComment").value(ticket.getDoctorComment());
            response.getJsonWriter().endObject();
        }

        response.getJsonWriter().endArray();
        response.setResponseCode(ResponseCode.OkCode);
    }
}
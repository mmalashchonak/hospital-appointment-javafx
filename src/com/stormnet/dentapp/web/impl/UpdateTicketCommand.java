package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.serverservice.TicketService;
import com.stormnet.dentapp.serverservice.factory.ServiceFactory;
import com.stormnet.dentapp.web.common.Command;
import com.stormnet.dentapp.web.common.Request;
import com.stormnet.dentapp.web.common.Response;
import com.stormnet.dentapp.web.socket.ResponseCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateTicketCommand implements Command {

    @Override
    public void execute(Request request, Response response) {

        String idStr = (String) request.getParameter("id");
        Integer id = Integer.valueOf(idStr);

        if (idStr.equals("")) {
            System.out.println("Null id received");
            response.setResponseCode(ResponseCode.NotFoundCode);
            return;
        }

        String clientIdStr = (String) request.getParameter("clientId");
        Integer clientId = Integer.valueOf(clientIdStr);

        String isFinishedStr = (String) request.getParameter("isFinished");
        Boolean isFinished = Boolean.valueOf(isFinishedStr);

        String doctorLastNameStr = (String) request.getParameter("doctorLastName");

        String doctorFirstNameStr = (String) request.getParameter("doctorFirstName");

        String clientLastNameStr = (String) request.getParameter("clientLastName");

        String clientFirstNameStr = (String) request.getParameter("clientFirstName");

        String dateStr = (String) request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(dateStr, formatter);

        String timeStr = (String) request.getParameter("time");

        String doctorIdStr = (String) request.getParameter("doctorId");
        Integer doctorId = Integer.valueOf(doctorIdStr);

        String cabinetStr = (String) request.getParameter("cabinet");

        String ratingStr = (String) request.getParameter("rating");

        String clientCommentStr = (String) request.getParameter("clientComment");

        String doctorCommentStr = (String) request.getParameter("doctorComment");

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setFinished(isFinished);
        ticket.setClientId(clientId);
        ticket.setDoctorLastName(doctorLastNameStr);
        ticket.setDoctorFirstName(doctorFirstNameStr);
        ticket.setClientLastName(clientLastNameStr);
        ticket.setClientFirstName(clientFirstNameStr);
        ticket.setDate(date);
        ticket.setTime(timeStr);
        ticket.setDoctorId(doctorId);
        ticket.setCabinet(cabinetStr);
        ticket.setRating(ratingStr);
        ticket.setClientComment(clientCommentStr);
        ticket.setDoctorComment(doctorCommentStr);

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        ticketService.update(ticket);

        response.setResponseCode(ResponseCode.OkCode);
    }
}

package com.stormnet.dentapp.dao.xml;

import com.stormnet.dentapp.dao.TicketDao;
import com.stormnet.dentapp.dao.exception.InvalidIdException;
import com.stormnet.dentapp.dao.exception.ObjectAlreadyStoredException;
import com.stormnet.dentapp.dao.exception.ObjectNotFoundException;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.db.xml.XmlDb;
import com.stormnet.dentapp.db.xml.XmlDbTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class XmlTicketDao implements TicketDao {

    @Override
    public List<Ticket> loadAll() {

        XmlDb xmlDb = XmlDb.getDb();
        Document ticketDocument = xmlDb.getXmlDocumentForTable(XmlDbTable.Tickets);

        List<Ticket> allTickets = new ArrayList<>();
        NodeList ticketTagList = ticketDocument.getElementsByTagName("ticket");
        for (int i = 0; i < ticketTagList.getLength(); i++) {
            Element ticketTag = (Element) ticketTagList.item(i);

            String idStr = ticketTag.getAttribute("id");
            Integer ticketId = Integer.valueOf(idStr);

            String clientIdStr = ticketTag.getAttribute("clientId");
            Integer clientId = Integer.valueOf(clientIdStr);

            String isFinishedStr = ticketTag.getAttribute("isFinished");
            Boolean isFinished = Boolean.valueOf(isFinishedStr);

            String doctorLastNameStr = ticketTag.getAttribute("doctorLastName");

            String doctorFirstNameStr = ticketTag.getAttribute("doctorFirstName");

            String clientLastNameStr = ticketTag.getAttribute("clientLastName");

            String clientFirstNameStr = ticketTag.getAttribute("clientFirstName");

            String dateStr = ticketTag.getAttribute("date");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate date = LocalDate.parse(dateStr, formatter);

            String timeStr = ticketTag.getAttribute("time");

            String doctorIdStr = ticketTag.getAttribute("doctorId");
            Integer doctorId = Integer.valueOf(doctorIdStr);

            String cabinetStr = ticketTag.getAttribute("cabinet");

            String ratingStr = ticketTag.getAttribute("rating");

            String clientCommentStr = ticketTag.getAttribute("clientComment");

            String doctorCommentStr = ticketTag.getAttribute("doctorComment");

            Ticket ticket = new Ticket();
            ticket.setId(ticketId);
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

            allTickets.add(ticket);
        }

        return allTickets;
    }

    @Override
    public Ticket loadById(Integer ticketId) {
        if (ticketId == null) {
            throw new InvalidIdException();
        }

        List<Ticket> allTickets = loadAll();
        for (Ticket ticket : allTickets) {
            if (ticketId.equals(ticket.getId())) {
                return ticket;
            }
        }

        throw new ObjectNotFoundException();
    }

    @Override
    public void save(Ticket ticket) {
        Integer ticketId = ticket.getId();
        if (ticketId != null) {
            throw new ObjectAlreadyStoredException();
        }

        XmlDb xmlDb = XmlDb.getDb();

        ticketId = XmlDb.getDb().getNextIdForTable();
        ticket.setId(ticketId);

        Document ticketDocument = xmlDb.getXmlDocumentForTable(XmlDbTable.Tickets);

        Element ticketTag = ticketDocument.createElement("ticket");

        ticketTag.setAttribute("id", ticketId.toString());
        ticketTag.setAttribute("clientId", ticket.getClientId().toString());
        ticketTag.setAttribute("isFinished", "false");
        ticketTag.setAttribute("doctorLastName", ticket.getDoctorLastName());
        ticketTag.setAttribute("doctorFirstName", ticket.getDoctorFirstName());
        ticketTag.setAttribute("clientLastName", ticket.getClientLastName());
        ticketTag.setAttribute("clientFirstName", ticket.getClientFirstName());
        ticketTag.setAttribute("doctorId", ticket.getDoctorId().toString());
        ticketTag.setAttribute("date", ticket.getDate().toString());
        ticketTag.setAttribute("time", ticket.getTime());
        ticketTag.setAttribute("cabinet", ticket.getCabinet());
        ticketTag.setAttribute("rating", ticket.getRating());
        ticketTag.setAttribute("clientComment", ticket.getClientComment());
        ticketTag.setAttribute("doctorComment", ticket.getDoctorComment());

        ticketDocument.getDocumentElement().appendChild(ticketTag);

        xmlDb.saveDocumentForTable(XmlDbTable.Tickets);

    }

    @Override
    public void update(Ticket ticket) {
        Integer ticketId = ticket.getId();
        if (ticketId == null) {
            return;
        }
        Ticket storedTicket = loadById(ticketId);
        if (storedTicket == null) {
            return;
        }

        XmlDb xmlDb = XmlDb.getDb();
        Document ticketDocument = xmlDb.getXmlDocumentForTable(XmlDbTable.Tickets);

        Element ticketTag = ticketDocument.createElement("ticket");

        ticketTag.setAttribute("id", ticketId.toString());
        ticketTag.setAttribute("clientId", ticket.getClientId().toString());
        ticketTag.setAttribute("isFinished", ticket.getFinished().toString());
        ticketTag.setAttribute("doctorLastName", ticket.getDoctorLastName());
        ticketTag.setAttribute("doctorFirstName", ticket.getDoctorFirstName());
        ticketTag.setAttribute("clientLastName", ticket.getClientLastName());
        ticketTag.setAttribute("clientFirstName", ticket.getClientFirstName());
        ticketTag.setAttribute("doctorId", ticket.getDoctorId().toString());
        ticketTag.setAttribute("date", ticket.getDate().toString());
        ticketTag.setAttribute("time", ticket.getTime());
        ticketTag.setAttribute("cabinet", ticket.getCabinet());
        ticketTag.setAttribute("rating", ticket.getRating());
        ticketTag.setAttribute("clientComment", ticket.getClientComment());
        ticketTag.setAttribute("doctorComment", ticket.getDoctorComment());

        xmlDb.updateDocumentForTable(ticketId, "ticket", ticketTag, XmlDbTable.Tickets);

    }

    @Override
    public void delete(Integer ticketId) {
        if (ticketId == null) {
            return;
        }
        XmlDb xmlDb = XmlDb.getDb();
        xmlDb.deleteFromDocumentForTable(ticketId, "ticket", XmlDbTable.Tickets);

    }
}

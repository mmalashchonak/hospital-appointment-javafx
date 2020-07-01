package com.stormnet.dentapp.dao.memory;

import com.stormnet.dentapp.dao.TicketDao;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.db.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class MemoryTicketDao implements TicketDao {

    private static List<Ticket> allTicketDb = initTicketDb();

    public MemoryTicketDao() {
    }

    @Override
    public List<Ticket> loadAll() {
        return allTicketDb;
    }

    @Override
    public Ticket loadById(Integer ticketID) {
        for (Ticket ticket: allTicketDb) {
            if (ticket.getId().equals(ticketID)) {
                return ticket;
            }
        }

        return null;
    }

    @Override
    public void save(Ticket ticket) {
        Integer ticketId = IdGenerator.getGenerator().nextId();
        ticket.setId(ticketId);

        allTicketDb.add(ticket);

    }

    @Override
    public void update(Ticket ticket) {
        Integer ticketID = ticket.getId();
        if (ticketID == null) {
            return;
        }

        Ticket dbTicket = loadById(ticketID);
        dbTicket.setTime(ticket.getTime());
        dbTicket.setDoctorLastName(ticket.getDoctorLastName());

    }

    @Override
    public void delete(Integer ticketId) {
        for (Ticket ticket: allTicketDb) {
            if (ticket.getId().equals(ticketId)) {
                allTicketDb.remove(ticket);
                return;
            }
        }
    }

    private static List<Ticket> initTicketDb() {
        List<Ticket> ticketsDb = new ArrayList<>();

        Ticket t1 = new Ticket();
        t1.setId(3);
        t1.setClientId(1);
        t1.setDoctorId(2);
        t1.setDoctorLastName("Ivan");
        t1.setDoctorLastName("Ivanov");
        t1.setClientFirstName("Peter");
        t1.setDoctorLastName("Petrov");

        t1.setFinished(false);

        Ticket t2 = new Ticket();
        t2.setId(4);
        t2.setClientId(2);
        t2.setDoctorId(2);
        t2.setDoctorLastName("Ivan");
        t2.setDoctorLastName("Ivanov");
        t2.setClientFirstName("Peter");
        t2.setDoctorLastName("Loginov");

        t2.setFinished(true);

        ticketsDb.add(t1);
        ticketsDb.add(t2);

        return ticketsDb;
    }
}

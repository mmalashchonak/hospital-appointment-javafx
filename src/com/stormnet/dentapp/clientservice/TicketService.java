package com.stormnet.dentapp.clientservice;

import com.stormnet.dentapp.bo.Ticket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface TicketService {

    void save(Ticket ticket) throws IOException;

    List<Ticket> loadAll() throws IOException;

    Ticket loadById(Integer id) throws IOException;

    void delete(Integer id) throws IOException;

    void update(Ticket ticket) throws IOException;
}

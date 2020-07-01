package com.stormnet.dentapp.serverservice;

import com.stormnet.dentapp.bo.Ticket;

import java.util.List;

public interface TicketService {

    void save (Ticket ticket);

    List<Ticket> loadAll();

    Ticket loadById (Integer id);

    void delete (Integer id);

    void update (Ticket ticket);
}

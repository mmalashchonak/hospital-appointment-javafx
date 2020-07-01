package com.stormnet.dentapp.serverservice.impl;

import com.stormnet.dentapp.dao.TicketDao;
import com.stormnet.dentapp.dao.factory.DaoFactory;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.serverservice.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    private TicketDao dao;

    public TicketServiceImpl() {
        this.dao = DaoFactory.getFactory().getTicketDao();
    }

    @Override
    public void save(Ticket ticket) {
        dao.save(ticket);
    }


    @Override
    public List<Ticket> loadAll() {
        return dao.loadAll();
    }

    @Override
    public Ticket loadById(Integer id) {
        return dao.loadById(id);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public void update(Ticket ticket) {
        dao.update(ticket);
    }
}

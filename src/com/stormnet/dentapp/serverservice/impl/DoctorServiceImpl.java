package com.stormnet.dentapp.serverservice.impl;

import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.factory.DaoFactory;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.serverservice.PersonService;

import java.util.List;

public class DoctorServiceImpl implements PersonService<Doctor> {

    private PersonDao<Doctor> dao;

    public DoctorServiceImpl() {
        this.dao = DaoFactory.getFactory().getDoctorDao();
    }

    @Override
    public void save(Doctor person) {
        dao.save(person);
    }

    @Override
    public List<Doctor> loadAll() {
        return dao.loadAll();
    }

    @Override
    public Doctor loadById(Integer id) {
        return dao.loadById(id);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public void update(Doctor person) {
        dao.update(person);
    }

    @Override
    public Doctor loadPersonByLoginAndPassword(String login, String password) {
        return dao.loadPersonByLoginAndPassword(login, password);
    }
}
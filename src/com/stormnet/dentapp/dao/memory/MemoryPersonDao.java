package com.stormnet.dentapp.dao.memory;

import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.bo.Id;
import com.stormnet.dentapp.bo.Person;

import java.util.List;

public class MemoryPersonDao implements PersonDao {
    @Override
    public Person loadPersonByLoginAndPassword(String login, String password) {
        return null;
    }

    @Override
    public List loadAll() {
        return null;
    }

    @Override
    public Id loadById(Integer id) {
        return null;
    }

    @Override
    public void save(Id object) {

    }

    @Override
    public void update(Id object) {

    }

    @Override
    public void delete(Integer id) {

    }

    //    private static List<Person> allPersonsDb = initMemoryDb();
//
//    public MemoryPersonDao() {
//    }
//
//    @Override
//    public List<Person> loadAll() {
//        return allPersonsDb;
//    }
//
//    @Override
//    public Person loadById(Integer personId) {
//        for (Person person: allPersonsDb) {
//            if (person.getId().equals(personId)) {
//                return person;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public Person save(Person person) {
//        Integer personId = IdGenerator.getGenerator().nextId();
//        person.setId(personId);
//
//        allPersonsDb.add(person);
//
//        return person;
//    }
//
//    @Override
//    public void update(Person person) {
//        Integer personId = person.getId();
//        if (personId == null) {
//            return;
//        }
//
//        Person dbPerson = loadById(personId);
//        dbPerson.setFirstName(person.getFirstName());
//        dbPerson.setLastName(person.getLastName());
//
//    }
//
//    @Override
//    public void delete(Integer personId) {
//        for (Person person: allPersonsDb) {
//            if (person.getId().equals(personId)) {
//                allPersonsDb.remove(person);
//                return;
//            }
//        }
//    }
//
//
//    public Person loadPersonByLoginAndPassword(String login, String password) {
//        for (Person person: allPersonsDb) {
//            if (person.getLogin().equals(login) && person.getPassword().equals(password)) {
//                return person;
//            }
//        }
//
//        return null;
//    }
//
//    private static List<Person> initMemoryDb() {
//        List<Person> personsDb = new ArrayList<>();
//
//        Person p1 = new Client();
//        p1.setId(IdGenerator.getGenerator().nextId());
//        p1.setFirstName("Ivan");
//        p1.setLastName("Ivanov");
//        p1.setLogin("p1");
//        p1.setPassword("123");
//
//
//
//
//        Person p2 = new Client();
//        p2.setId(IdGenerator.getGenerator().nextId());
//        p2.setFirstName("Petr");
//        p2.setLastName("Petrov");
//        p2.setLogin("p2");
//        p2.setPassword("123");
//
//
//
//        Person p3 = new Client();
//        p3.setId(IdGenerator.getGenerator().nextId());
//        p3.setFirstName("Sidor");
//        p3.setLastName("Sidorov");
//        p3.setLogin("p3");
//        p3.setPassword("123");
//
//
//
//        personsDb.add(p1);
//        personsDb.add(p2);
//        personsDb.add(p3);
//
//        return personsDb;
//    }
}

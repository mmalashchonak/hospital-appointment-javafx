package com.stormnet.dentapp.dao.xml;

import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.exception.InvalidIdException;
import com.stormnet.dentapp.dao.exception.ObjectAlreadyStoredException;
import com.stormnet.dentapp.dao.exception.ObjectNotFoundException;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.db.xml.XmlDb;
import com.stormnet.dentapp.db.xml.XmlDbTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;

public class XmlDoctorDao implements PersonDao<Doctor> {



    @Override
    public Doctor loadPersonByLoginAndPassword(String login, String password) {
        List<Doctor> all = loadAll();

        for (Doctor doctor : all) {
            if (doctor.getLogin().equals(login) && doctor.getPassword().equals(password)) {
                return doctor;
            }
        }

        return null;
    }

    @Override
    public List<Doctor> loadAll() {
        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Doctors);

        List<Doctor> all = new ArrayList<>();
        NodeList tagList = document.getElementsByTagName("person");
        for (int i = 0; i < tagList.getLength(); i++) {
            Element tag = (Element) tagList.item(i);

            String idStr = tag.getAttribute("id");
            Integer id = Integer.valueOf(idStr);

            String firstNameStr = tag.getAttribute("firstName");

            String passwordStr = tag.getAttribute("password");

            String loginStr = tag.getAttribute("login");

            String lastNameStr = tag.getAttribute("lastName");

            String cabinetStr = tag.getAttribute("cabinet");

            Doctor person = new Doctor();
            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setPassword(passwordStr);
            person.setLogin(loginStr);
            person.setCabinet(cabinetStr);

            all.add(person);
        }

        return all;
    }

    @Override
    public Doctor loadById(Integer id) {
        if (id == null) {
            throw new InvalidIdException();
        }

        List<Doctor> all = loadAll();
        for (Doctor doctor : all) {
            if (id.equals(doctor.getId())) {
                return doctor;
            }
        }

        throw new ObjectNotFoundException();
    }

    @Override
    public void save(Doctor object) {
        Integer id = object.getId();
        if (id != null) {
            throw new ObjectAlreadyStoredException();
        }

        XmlDb xmlDb = XmlDb.getDb();

        id = XmlDb.getDb().getNextIdForTable();
        object.setId(id);

        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Doctors);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());
        tag.setAttribute("cabinet", object.getCabinet());

        document.getDocumentElement().appendChild(tag);

        xmlDb.saveDocumentForTable(XmlDbTable.Doctors);

    }

    @Override
    public void update(Doctor object) {

        Integer id = object.getId();
        if (id == null) {
            return;
        }
        Doctor stored = loadById(id);
        if (stored == null) {
            return;
        }

        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Doctors);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());
        tag.setAttribute("cabinet", object.getCabinet());

        document.getDocumentElement().appendChild(tag);

        xmlDb.updateDocumentForTable(id, "person", tag, XmlDbTable.Doctors);

    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        XmlDb xmlDb = XmlDb.getDb();
        xmlDb.deleteFromDocumentForTable(id, "person", XmlDbTable.Doctors);

    }
}


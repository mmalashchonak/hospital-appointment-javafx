package com.stormnet.dentapp.dao.xml;

import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.exception.InvalidIdException;
import com.stormnet.dentapp.dao.exception.ObjectAlreadyStoredException;
import com.stormnet.dentapp.dao.exception.ObjectNotFoundException;
import com.stormnet.dentapp.db.xml.XmlDb;
import com.stormnet.dentapp.db.xml.XmlDbTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XmlAdminDao implements PersonDao<Admin> {


    @Override
    public Admin loadPersonByLoginAndPassword(String login, String password) {
        List<Admin> all = loadAll();

        for (Admin admin : all) {
            if (admin.getLogin().equals(login) && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        return null;
    }

    @Override
    public List<Admin> loadAll() {
        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Admins);

        List<Admin> all = new ArrayList<>();
        NodeList tagList = document.getElementsByTagName("person");
        for (int i = 0; i < tagList.getLength(); i++) {
            Element tag = (Element) tagList.item(i);

            String idStr = tag.getAttribute("id");
            Integer id = Integer.valueOf(idStr);

            String firstNameStr = tag.getAttribute("firstName");

            String passwordStr = tag.getAttribute("password");

            String loginStr = tag.getAttribute("login");

            String lastNameStr = tag.getAttribute("lastName");

            Admin person = new Admin();
            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setPassword(passwordStr);
            person.setLogin(loginStr);

            all.add(person);
        }

        return all;
    }

    @Override
    public Admin loadById(Integer id) {
        if (id == null) {
            throw new InvalidIdException();
        }

        List<Admin> all = loadAll();
        for (Admin admin : all) {
            if (id.equals(admin.getId())) {
                return admin;
            }
        }

        throw new ObjectNotFoundException();
    }

    @Override
    public void save(Admin object) {
        Integer id = object.getId();
        if (id != null) {
            throw new ObjectAlreadyStoredException();
        }

        XmlDb xmlDb = XmlDb.getDb();

        id = XmlDb.getDb().getNextIdForTable();
        object.setId(id);

        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Admins);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());

        document.getDocumentElement().appendChild(tag);

        xmlDb.saveDocumentForTable(XmlDbTable.Admins);

    }

    @Override
    public void update(Admin object) {

        Integer id = object.getId();
        if (id == null) {
            return;
        }
        Admin stored = loadById(id);
        if (stored == null) {
            return;
        }

        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Admins);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());

        document.getDocumentElement().appendChild(tag);

        xmlDb.updateDocumentForTable(id, "person", tag, XmlDbTable.Admins);

    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        XmlDb xmlDb = XmlDb.getDb();
        xmlDb.deleteFromDocumentForTable(id, "person", XmlDbTable.Admins);

    }
}
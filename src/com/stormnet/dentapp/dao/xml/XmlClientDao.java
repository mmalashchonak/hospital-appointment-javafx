package com.stormnet.dentapp.dao.xml;

import com.stormnet.dentapp.dao.PersonDao;
import com.stormnet.dentapp.dao.exception.InvalidIdException;
import com.stormnet.dentapp.dao.exception.ObjectAlreadyStoredException;
import com.stormnet.dentapp.dao.exception.ObjectNotFoundException;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.db.xml.XmlDb;
import com.stormnet.dentapp.db.xml.XmlDbTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;

public class XmlClientDao implements PersonDao<Client> {


    @Override
    public Client loadPersonByLoginAndPassword(String login, String password) {
        List<Client> all = loadAll();

        for (Client client : all) {
            if (client.getLogin().equals(login) && client.getPassword().equals(password)) {
                return client;
            }
        }

        return null;
    }

    @Override
    public List<Client> loadAll() {
        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Clients);

        List<Client> all = new ArrayList<>();
        NodeList tagList = document.getElementsByTagName("person");
        for (int i = 0; i < tagList.getLength(); i++) {
            Element tag = (Element) tagList.item(i);

            String idStr = tag.getAttribute("id");
            Integer id = Integer.valueOf(idStr);

            String firstNameStr = tag.getAttribute("firstName");

            String passwordStr = tag.getAttribute("password");

            String loginStr = tag.getAttribute("login");

            String lastNameStr = tag.getAttribute("lastName");

            Client person = new Client();
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
    public Client loadById(Integer id) {
        if (id == null) {
            throw new InvalidIdException();
        }

        List<Client> all = loadAll();
        for (Client client : all) {
            if (id.equals(client.getId())) {
                return client;
            }
        }

        throw new ObjectNotFoundException();
    }

    @Override
    public void save(Client object) {
        Integer id = object.getId();
        if (id != null) {
            throw new ObjectAlreadyStoredException();
        }

        XmlDb xmlDb = XmlDb.getDb();

        id = XmlDb.getDb().getNextIdForTable();
        object.setId(id);

        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Clients);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());

        document.getDocumentElement().appendChild(tag);

        xmlDb.saveDocumentForTable(XmlDbTable.Clients);

    }

    @Override
    public void update(Client object) {

        Integer id = object.getId();
        if (id == null) {
            return;
        }
        Client stored = loadById(id);
        if (stored == null) {
            return;
        }

        XmlDb xmlDb = XmlDb.getDb();
        Document document = xmlDb.getXmlDocumentForTable(XmlDbTable.Clients);

        Element tag = document.createElement("person");

        tag.setAttribute("id", Integer.toString(id));
        tag.setAttribute("firstName", object.getFirstName());
        tag.setAttribute("lastName", object.getLastName());
        tag.setAttribute("password", object.getPassword());
        tag.setAttribute("login", object.getLogin());

        document.getDocumentElement().appendChild(tag);

        xmlDb.updateDocumentForTable(id, "person", tag, XmlDbTable.Clients);

    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        XmlDb xmlDb = XmlDb.getDb();
        xmlDb.deleteFromDocumentForTable(id, "person", XmlDbTable.Clients);

    }
}

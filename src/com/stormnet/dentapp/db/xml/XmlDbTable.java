package com.stormnet.dentapp.db.xml;

public enum XmlDbTable {

    Tickets("db/tickets.xml"),
    Clients("db/clients.xml"),
    Admins("db/admins.xml"),
    Doctors("db/doctors.xml"),
    Settings("settings/settings.xml");

    private String xmlFilePath;

    XmlDbTable(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getXmlFilePath() {
        return this.xmlFilePath;
    }
}

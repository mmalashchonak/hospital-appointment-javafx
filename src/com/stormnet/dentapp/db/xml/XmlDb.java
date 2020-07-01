package com.stormnet.dentapp.db.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class XmlDb {

    private static final XmlDb db = new XmlDb();

    private Map<XmlDbTable, Document> xmlDbDocuments;

    public static XmlDb getDb() {
        return db;
    }

    private XmlDb() {
        xmlDbDocuments = new HashMap<>();
    }

    public Document getXmlDocumentForTable(XmlDbTable table) {
        Document document = xmlDbDocuments.get(table);
        if (document == null) {
            document = loadDocumentForTable(table);
            xmlDbDocuments.put(table, document);
        }
        return document;
    }

    public Document saveDocumentForTable(XmlDbTable table) throws RuntimeException {
        File xmlFile = getXmlFile(table);
        Document document = getDb().getXmlDocumentForTable(table);
        xmlFile.delete();

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            throw new XmlDbException(e.getCause());
        }
        return document;

    }

    public Document loadDocumentForTable(XmlDbTable table) {
        File xmlFile = getDb().getXmlFile(table);

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            return document;
        } catch (Exception e) {
            throw new XmlDbException(e.getCause());
        }

    }

    public Integer getNextIdForTable() {

        Document document = getXmlDocumentForTable(XmlDbTable.Settings);
        NodeList idTag = document.getElementsByTagName("max-id");

        String maxId = idTag.item(0).getTextContent();
        Integer id = Integer.parseInt(maxId);
        id++;

        idTag.item(0).setTextContent(id.toString());
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        File xmlFile = getXmlFile(XmlDbTable.Settings);
        xmlFile.delete();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public File getXmlFile(XmlDbTable table) {
        File xmlFile = new File(table.getXmlFilePath());
        return xmlFile;
    }

    public void deleteFromDocumentForTable(Integer id, String tagName, XmlDbTable table) {
        Document document = getXmlDocumentForTable(table);

        File xmlFile = getDb().getXmlFile(table);
        xmlFile.delete();

        NodeList ticketTagList = document.getElementsByTagName(tagName);
        for (int i = 0; i < ticketTagList.getLength(); i++) {
            Element ticketTag = (Element) ticketTagList.item(i);

            String idStr = ticketTag.getAttribute("id");
            Integer ticketId = Integer.valueOf(idStr);
            if (ticketId.equals(id)) {
                ticketTagList.item(i).getParentNode().removeChild(ticketTag);
                document.normalize();
            }
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            throw new XmlDbException(e.getCause());
        }
    }

    public void updateDocumentForTable(Integer id, String tagName, Element newTag, XmlDbTable table) {
        Document document = getXmlDocumentForTable(table);
        File xmlFile = getDb().getXmlFile(table);

        NodeList tagList = document.getElementsByTagName(tagName);
        for (int i = 0; i < tagList.getLength(); i++) {
            Element objectTag = (Element) tagList.item(i);

            String idStr = objectTag.getAttribute("id");
            Integer objectId = Integer.valueOf(idStr);
            if (objectId.equals(id)) {
                objectTag.getParentNode().replaceChild(newTag, objectTag);
                document.normalize();
            }
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            throw new XmlDbException(e.getCause());
        }
    }



}



package com.stormnet.dentapp.db.xml;

public class XmlDbException extends RuntimeException {

    public XmlDbException(Throwable cause) {
        super("Error occurred during access to XML DB", cause);

    }
}

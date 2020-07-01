package com.stormnet.dentapp.web.common;

public interface Request {

    String getCommandName();

    Object getParameter(String paramName);
}

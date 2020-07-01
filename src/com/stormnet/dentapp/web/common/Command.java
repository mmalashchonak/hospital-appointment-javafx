package com.stormnet.dentapp.web.common;

public interface Command {

    void execute(Request request, Response response);

}

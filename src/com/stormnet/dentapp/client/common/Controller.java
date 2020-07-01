package com.stormnet.dentapp.client.common;

import java.io.IOException;

public interface Controller<T> {

    void reloadWindow() throws IOException;

    void setFormObject(T object) throws IOException;
}

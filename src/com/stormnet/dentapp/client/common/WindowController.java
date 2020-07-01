package com.stormnet.dentapp.client.common;

import javafx.stage.Stage;

import java.io.IOException;

public class WindowController<T> {

    private Stage window;

    private Controller<T> controller;

    public WindowController(Stage window, Controller controller) {
        this.window = window;
        this.controller = controller;
    }

    public Stage getWindow() {
        return window;
    }

    public Controller getController() {
        return controller;
    }

    public void showWindow() {
        window.show();
    }

    public void hideWindow() {
        window.hide();
    }

    public void reloadWindow() throws IOException {
        controller.reloadWindow();
    }

    public void setFormObject(T object) throws IOException {
        controller.setFormObject(object);
    }
}
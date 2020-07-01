package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.client.exceptions.LoginIsAlreadySetException;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class ClientRegistrationController implements Controller {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;


    public void saveBtnPressed() throws IOException {
        if (firstName.getText().equals("") || lastName.getText().equals("") || login.getText().equals("") || password.getText().equals("")) {
            throw new RuntimeException("Please, fill all fields.");
        }

        PersonService<Client> clientService = ServiceFactory.getServiceFactory().getClientService();
        List<Client> allClients = clientService.loadAll();
        for (Client client : allClients) {
            String clientLogin = client.getLogin();
            if (login.getText().equals(clientLogin)) {
                throw new LoginIsAlreadySetException();
            }
        }

        Client client = new Client();
        client.setFirstName(firstName.getText());
        client.setLastName(lastName.getText());
        client.setLogin(login.getText());
        client.setPassword(password.getText());

        clientService.save(client);
        AppWindows.getInstance().hideWindow(WindowConfigs.ClientRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.ClientLoginWindow);
    }

    public void cancelBtnPressed() {
        AppWindows.getInstance().hideWindow(WindowConfigs.ClientRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.ClientLoginWindow);


    }

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}

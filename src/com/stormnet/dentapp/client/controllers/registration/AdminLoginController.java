package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.Session;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminLoginController implements Controller {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    public void SignInBtnPressed() throws IOException {

        AppWindows appWindows = AppWindows.getInstance();
        PersonService<Admin> adminService = ServiceFactory.getServiceFactory().getAdminService();

        if (adminService.loadPersonByLoginAndPassword(login.getText(), password.getText()) == null) {
            appWindows.createWindow(WindowConfigs.WrongLoginPasswordWindow);
            appWindows.showWindow(WindowConfigs.WrongLoginPasswordWindow);
        } else {
            Session newSession = Session.getInstance();
            int currentSessionId = adminService.loadPersonByLoginAndPassword(login.getText(), password.getText()).getId();
            newSession.setPersonId(currentSessionId);
            appWindows.hideWindow(WindowConfigs.AdminLoginWindow);
            appWindows.createWindow(WindowConfigs.AdminMainTicketsWindow);
            appWindows.showWindow(WindowConfigs.AdminMainTicketsWindow);
            appWindows.reloadWindow(WindowConfigs.AdminMainTicketsWindow);
        }
    }

    @FXML
    public void RegistrationBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminLoginWindow);
        appWindows.createWindow(WindowConfigs.AdminRegistrationWindow);
        appWindows.showWindow(WindowConfigs.AdminRegistrationWindow);
    }

    @FXML
    public void ExitBtnPressed() {
        System.exit(0);
    }


    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}

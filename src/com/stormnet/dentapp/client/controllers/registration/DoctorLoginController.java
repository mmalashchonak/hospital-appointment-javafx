package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.Session;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DoctorLoginController implements Controller {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    public void SignInBtnPressed() throws IOException {

        AppWindows appWindows = AppWindows.getInstance();
        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();

        if (doctorService.loadPersonByLoginAndPassword(login.getText(), password.getText()) == null) {
            appWindows.createWindow(WindowConfigs.WrongLoginPasswordWindow);
            appWindows.showWindow(WindowConfigs.WrongLoginPasswordWindow);
        } else {
            Session newSession = Session.getInstance();
            int currentSessionId = doctorService.loadPersonByLoginAndPassword(login.getText(), password.getText()).getId();
            newSession.setPersonId(currentSessionId);
            appWindows.hideWindow(WindowConfigs.DoctorLoginWindow);
            appWindows.createWindow(WindowConfigs.DoctorMainWindow);
            appWindows.reloadWindow(WindowConfigs.DoctorMainWindow);
            appWindows.showWindow(WindowConfigs.DoctorMainWindow);
        }
    }

    public void RegistrationBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.DoctorLoginWindow);
        appWindows.createWindow(WindowConfigs.DoctorRegistrationWindow);
        appWindows.showWindow(WindowConfigs.DoctorRegistrationWindow);
    }

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

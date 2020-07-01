package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.client.exceptions.LoginIsAlreadySetException;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class DoctorRegistrationController implements Controller {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField cabinet;

    @FXML
    private PasswordField keypass;


    public void saveBtnPressed() throws IOException {
        if (firstName.getText().equals("") || lastName.getText().equals("") || login.getText().equals("") || password.getText().equals("") || cabinet.getText().equals("")) {
            throw new RuntimeException("Please, fill all fields.");
        }

        if (!keypass.getText().equals(Doctor.getDoctorPass())) {
            throw new RuntimeException("Invalid keypass!");
        }

        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        List<Doctor> all = doctorService.loadAll();
        for (Doctor doctor : all) {
            String doctorLogin = doctor.getLogin();
            if (login.getText().equals(doctorLogin)) {
                throw new LoginIsAlreadySetException();
            }
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName.getText());
        doctor.setLastName(lastName.getText());
        doctor.setLogin(login.getText());
        doctor.setPassword(password.getText());
        doctor.setCabinet(cabinet.getText());

        doctorService.save(doctor);
        AppWindows.getInstance().hideWindow(WindowConfigs.DoctorRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.DoctorLoginWindow);
    }

    public void cancelBtnPressed() {
        AppWindows.getInstance().hideWindow(WindowConfigs.DoctorRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.DoctorLoginWindow);


    }

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}

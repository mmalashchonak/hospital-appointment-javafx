package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.client.exceptions.LoginIsAlreadySetException;
import com.stormnet.dentapp.bo.Admin;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class AdminRegistrationController implements Controller {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField keypass;


    public void saveBtnPressed() throws IOException {
        if (firstName.getText().equals("") || lastName.getText().equals("") || login.getText().equals("") || password.getText().equals("")) {
            throw new RuntimeException("Please, fill all fields.");
        }

        if (!keypass.getText().equals(Admin.getAdminPass())) {
            throw new RuntimeException("Invalid keypass!");
        }

        PersonService<Admin> adminService = ServiceFactory.getServiceFactory().getAdminService();
        List<Admin> all = adminService.loadAll();
        for (Admin admin : all) {
            String adminLogin = admin.getLogin();
            if (login.getText().equals(adminLogin)) {
                throw new LoginIsAlreadySetException();
            }
        }

        Admin admin = new Admin();
        admin.setFirstName(firstName.getText());
        admin.setLastName(lastName.getText());
        admin.setLogin(login.getText());
        admin.setPassword(password.getText());

        adminService.save(admin);
        AppWindows.getInstance().hideWindow(WindowConfigs.AdminRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.AdminLoginWindow);
    }

    public void cancelBtnPressed() {
        AppWindows.getInstance().hideWindow(WindowConfigs.AdminRegistrationWindow);
        AppWindows.getInstance().showWindow(WindowConfigs.AdminLoginWindow);


    }

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}

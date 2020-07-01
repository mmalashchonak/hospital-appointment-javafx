package com.stormnet.dentapp.client.controllers.doctor;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.Session;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class DoctorCabinetController implements Controller {

    @FXML
    private Label id;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Label login;

    @FXML
    private Label rating;

    @FXML
    private TextField cabinet;

    @FXML
    private PasswordField password;

    public void saveBtnPressed() throws IOException {
        if (firstName.getText().equals("") || lastName.getText().equals("") || password.getText().equals("") || cabinet.getText().equals("")) {
            throw new RuntimeException("Please, fill all fields.");
        }

        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        Doctor currentPerson = doctorService.loadById(Integer.valueOf(id.getText()));

        currentPerson.setFirstName(firstName.getText());
        currentPerson.setLastName(lastName.getText());
        currentPerson.setPassword(password.getText());
        currentPerson.setCabinet(cabinet.getText());

        doctorService.update(currentPerson);
        AppWindows.getInstance().hideWindow(WindowConfigs.DoctorCabinetWindow);
    }

    public void cancelBtnPressed() {
        AppWindows.getInstance().hideWindow(WindowConfigs.DoctorCabinetWindow);

    }

    @Override
    public void reloadWindow() throws IOException {
        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        Doctor currentPerson = doctorService.loadById(Session.getInstance().getPersonId());
        id.setText(currentPerson.getId().toString());
        firstName.setText(currentPerson.getFirstName());
        lastName.setText(currentPerson.getLastName());
        login.setText(currentPerson.getLogin());
        password.setText(currentPerson.getPassword());
        cabinet.setText(currentPerson.getCabinet());

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        List<Ticket> allTickets = ticketService.loadAll();
        int totalRateSum = 0;
        int marksCount = 0;
        double myRate = 0;
        for (Ticket ticket : allTickets) {
            if (!ticket.getRating().equals("") && ticket.getDoctorId().equals(Session.getInstance().getPersonId())) {
                totalRateSum += Integer.valueOf(ticket.getRating());
                marksCount++;
            }
        }

        if (marksCount != 0) {
            myRate = (double) totalRateSum / marksCount;
        }
        DecimalFormat ratingAccuracy = new DecimalFormat("#.00");
        rating.setText(String.valueOf(ratingAccuracy.format(myRate)));
    }

    @Override
    public void setFormObject(Object object) {
    }
}

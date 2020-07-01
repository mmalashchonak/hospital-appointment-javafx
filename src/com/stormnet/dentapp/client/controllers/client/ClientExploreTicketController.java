package com.stormnet.dentapp.client.controllers.client;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientExploreTicketController implements Controller<Ticket>, Initializable {

    @FXML
    private Label ticketId;

    @FXML
    private ComboBox<String> rating;

    @FXML
    private Label doctorFirstName;

    @FXML
    private Label doctorLastName;

    @FXML
    private Label date;

    @FXML
    private Label doctorComment;

    @FXML
    private Label time;

    @FXML
    private Label cabinet;

    @FXML
    private TextArea clientComment;

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Ticket object) {
        ticketId.setText(object.getId().toString());
        rating.setValue(object.getRating());
        clientComment.setText(object.getClientComment());
        doctorFirstName.setText(object.getDoctorFirstName());
        doctorLastName.setText(object.getDoctorLastName());
        date.setText(object.getDate().toString());
        time.setText(object.getTime());
        cabinet.setText(object.getCabinet());
        doctorComment.setText(object.getDoctorComment());
    }

    @FXML
    public void saveBtnPressed() throws IOException {
        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        Ticket ticket = ticketService.loadById(Integer.valueOf(ticketId.getText()));
        ticket.setClientComment(clientComment.getText());
        ticket.setRating(rating.getValue());
        ticketService.update(ticket);
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.ClientExploreTicketWindow);
        appWindows.reloadWindow(WindowConfigs.ClientMainWindow);
    }

    @FXML
    public void cancelBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.ClientExploreTicketWindow);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> allRates = rating.getItems();

        for (int i = 1; i < 6; i++) {
            allRates.add(String.valueOf(i));
        }
    }
}

package com.stormnet.dentapp.client.controllers.doctor;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class DoctorExploreTicketController implements Controller<Ticket> {

    @FXML
    private Label ticketId;

    @FXML
    private Label rating;

    @FXML
    private Label clientFirstName;

    @FXML
    private Label clientLastName;

    @FXML
    private Label date;

    @FXML
    private TextArea doctorComment;

    @FXML
    private Label time;

    @FXML
    private Label cabinet;

    @FXML
    private Label clientComment;

    @FXML
    private CheckBox isFinished;

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Ticket object) {
        ticketId.setText(object.getId().toString());
        rating.setText(object.getRating());
        clientComment.setText(object.getClientComment());
        doctorComment.setText(object.getDoctorComment());
        clientFirstName.setText(object.getClientFirstName());
        clientLastName.setText(object.getClientLastName());
        date.setText(object.getDate().toString());
        time.setText(object.getTime());
        cabinet.setText(object.getCabinet());
        doctorComment.setText(object.getDoctorComment());
        isFinished.setSelected(object.getFinished());
    }

    @FXML
    public void saveBtnPressed() throws IOException {
        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        Ticket ticket = ticketService.loadById(Integer.valueOf(ticketId.getText()));
        ticket.setDoctorComment(doctorComment.getText());
        ticket.setFinished(isFinished.isSelected());
        ticketService.update(ticket);
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.DoctorExploreTicketWindow);
        appWindows.reloadWindow(WindowConfigs.DoctorMainWindow);
    }

    @FXML
    public void cancelBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.DoctorExploreTicketWindow);
    }
}

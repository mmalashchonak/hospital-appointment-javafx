package com.stormnet.dentapp.client.controllers.admin;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminExploreTicketController implements Controller<Ticket> {

    @FXML
    private Label ticketId;

    @FXML
    private Label rating;

    @FXML
    private Label clientFirstName;

    @FXML
    private Label clientLastName;

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
    private Label clientComment;

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
        doctorFirstName.setText(object.getDoctorFirstName());
        doctorLastName.setText(object.getDoctorLastName());
        date.setText(object.getDate().toString());
        time.setText(object.getTime());
        cabinet.setText(object.getCabinet());
    }

    @FXML
    public void cancelBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminExploreTicketWindow);
    }
}

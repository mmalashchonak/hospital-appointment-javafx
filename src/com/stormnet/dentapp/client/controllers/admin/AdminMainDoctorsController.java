package com.stormnet.dentapp.client.controllers.admin;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMainDoctorsController implements Controller<Doctor>, Initializable {

    @FXML
    private TableView<Doctor> allDoctorsTable;

    @FXML
    public void allTicketsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminMainDoctorsWindow);
        appWindows.createWindow(WindowConfigs.AdminMainTicketsWindow);
        appWindows.reloadWindow(WindowConfigs.AdminMainTicketsWindow);
        appWindows.showWindow(WindowConfigs.AdminMainTicketsWindow);

    }

    @FXML
    public void allDoctorsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.reloadWindow(WindowConfigs.AdminMainDoctorsWindow);
    }

    @FXML
    public void allClientsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminMainDoctorsWindow);
        appWindows.createWindow(WindowConfigs.AdminMainClientsWindow);
        appWindows.reloadWindow(WindowConfigs.AdminMainClientsWindow);
        appWindows.showWindow(WindowConfigs.AdminMainClientsWindow);
    }

    @FXML
    public void personalCabinetBtnPressed() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.AdminCabinetWindow);
        appWindows.showWindow(WindowConfigs.AdminCabinetWindow);
        appWindows.reloadWindow(WindowConfigs.AdminCabinetWindow);
    }

    @FXML
    public void exitBtnPressed() {
        System.exit(0);
    }

    @FXML
    public void aboutBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.AboutWindow);
        appWindows.showWindow(WindowConfigs.AboutWindow);
    }

    @Override
    public void reloadWindow() throws IOException {
        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        List<Doctor> allDoctors = doctorService.loadAll();
        ObservableList<Doctor> gridItems = allDoctorsTable.getItems();
        gridItems.clear();
        gridItems.addAll(allDoctors);
    }

    @Override
    public void setFormObject(Doctor object) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Doctor, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setMinWidth(100);
        ratingColumn.setCellValueFactory(new RatingStringFactory());

        allDoctorsTable.getColumns().add(ratingColumn);

        TableColumn<Doctor, GridPane> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setMinWidth(50);
        actionsColumn.setCellValueFactory(new ButtonsCellFactory());

        allDoctorsTable.getColumns().add(actionsColumn);
    }

    private class RatingStringFactory implements Callback<TableColumn.CellDataFeatures<Doctor, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Doctor, String> param) {
            TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
            List<Ticket> allTickets = new ArrayList<>();
            try {
                allTickets = ticketService.loadAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int totalRateSum = 0;
            int marksCount = 0;
            double myRate = 0;
            for (Ticket ticket : allTickets) {
                if (!ticket.getRating().equals("") && ticket.getDoctorId().equals(param.getValue().getId())) {
                    totalRateSum += Integer.valueOf(ticket.getRating());
                    marksCount++;
                }
            }

            if (marksCount != 0) {
                myRate = (double) totalRateSum / marksCount;
            }

            DecimalFormat ratingAccuracy = new DecimalFormat("#.00");
            String rating = String.valueOf(ratingAccuracy.format(myRate));

            return new ReadOnlyStringWrapper(rating);
        }
    }

    private class ButtonsCellFactory implements Callback<TableColumn.CellDataFeatures<Doctor, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<Doctor, GridPane> param) {
            Doctor doctor = param.getValue();

            Button viewBtn = new Button("view tickets");
            viewBtn.setOnAction(new ViewTicketEvent(doctor));

            GridPane panel = new GridPane();
            panel.setHgap(2);
            panel.setVgap(2);
            panel.setPadding(new Insets(2, 2, 2, 2));

            panel.add(viewBtn, 0, 0);

            return new SimpleObjectProperty<>(panel);
        }
    }

    private class ViewTicketEvent implements EventHandler<ActionEvent> {

        private Doctor currentDoctor;

        public ViewTicketEvent(Doctor doctor) {
            this.currentDoctor = doctor;
        }

        @Override
        public void handle(ActionEvent event) {
            AppWindows appWindows = AppWindows.getInstance();

            appWindows.createWindow(WindowConfigs.AdminExploreDoctorTicketsWindow);
            try {
                appWindows.setFormObject(WindowConfigs.AdminExploreDoctorTicketsWindow, currentDoctor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            appWindows.showWindow(WindowConfigs.AdminExploreDoctorTicketsWindow);
        }
    }
}
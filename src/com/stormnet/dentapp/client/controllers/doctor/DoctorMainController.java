package com.stormnet.dentapp.client.controllers.doctor;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.Session;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Ticket;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorMainController implements Controller<Ticket>, Initializable {

    @FXML
    private TableView<Ticket> allTicketsTable;

    @FXML
    private CheckBox showThisDayTickets;

    public void personalCabinetBtnPressed() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.DoctorCabinetWindow);
        appWindows.showWindow(WindowConfigs.DoctorCabinetWindow);
        appWindows.reloadWindow(WindowConfigs.DoctorCabinetWindow);
    }

    public void exitBtnPressed() {
        System.exit(0);
    }

    public void aboutBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.AboutWindow);
        appWindows.showWindow(WindowConfigs.AboutWindow);
    }

    @Override
    public void reloadWindow() throws IOException {
        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        List<Ticket> allTickets = ticketService.loadAll();

        List<Ticket> myTickets = new ArrayList<>();

        for (Ticket ticket : allTickets) {
            int myId = ticket.getDoctorId();
            LocalDate date = ticket.getDate();
            if (myId == Session.getInstance().getPersonId() && showThisDayTickets.isSelected() && date.equals(LocalDate.now())) {
                myTickets.add(ticket);
            } else if (myId == Session.getInstance().getPersonId() && !showThisDayTickets.isSelected()) {
                myTickets.add(ticket);
            }
        }

        ObservableList<Ticket> gridItems = allTicketsTable.getItems();
        gridItems.clear();
        gridItems.addAll(myTickets);
    }

    @Override
    public void setFormObject(Ticket object) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Ticket, String> clientColumn = new TableColumn<>("Client");
        clientColumn.setMinWidth(100);
        clientColumn.setCellValueFactory(new ClientStringFactory());

        allTicketsTable.getColumns().add(clientColumn);


        TableColumn<Ticket, String> activeColumn = new TableColumn<>("Status");
        activeColumn.setMinWidth(100);
        activeColumn.setCellValueFactory(new IsActiveStringFactory());

        allTicketsTable.getColumns().add(activeColumn);


        TableColumn<Ticket, GridPane> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setMinWidth(50);
        actionsColumn.setCellValueFactory(new ButtonsCellFactory());

        allTicketsTable.getColumns().add(actionsColumn);
    }

    private class ClientStringFactory implements Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
            String client = param.getValue().getClientFirstName() + " " + param.getValue().getClientLastName();
            return new ReadOnlyStringWrapper(client);
        }
    }

    private class IsActiveStringFactory implements Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
            boolean isActive = param.getValue().getFinished();
            String activeAsString;
            if (isActive) {
                activeAsString = "Finished";
            } else {
                activeAsString = "Waiting";
            }

            return new ReadOnlyStringWrapper(activeAsString);
        }
    }

    private class ButtonsCellFactory implements Callback<TableColumn.CellDataFeatures<Ticket, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<Ticket, GridPane> param) {
            Ticket ticket = param.getValue();

            Button viewBtn = new Button("view");
            viewBtn.setOnAction(new ViewTicketEvent(ticket));

            GridPane panel = new GridPane();
            panel.setHgap(2);
            panel.setVgap(2);
            panel.setPadding(new Insets(2, 2, 2, 2));

            panel.add(viewBtn, 0, 0);

            return new SimpleObjectProperty<>(panel);
        }
    }


    private class ViewTicketEvent implements EventHandler<ActionEvent> {

        private Ticket currentTicket;

        public ViewTicketEvent(Ticket ticket) {
            this.currentTicket = ticket;
        }

        @Override
        public void handle(ActionEvent event) {
            AppWindows appWindows = AppWindows.getInstance();

            appWindows.createWindow(WindowConfigs.DoctorExploreTicketWindow);
            try {
                appWindows.setFormObject(WindowConfigs.DoctorExploreTicketWindow, currentTicket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            appWindows.showWindow(WindowConfigs.DoctorExploreTicketWindow);
        }
    }
}
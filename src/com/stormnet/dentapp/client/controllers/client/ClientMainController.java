package com.stormnet.dentapp.client.controllers.client;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientMainController implements Controller<Ticket>, Initializable {

    @FXML
    private TableView<Ticket> allTicketsTable;


    public void getTicketBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.ClientGetTicketWindow);
        appWindows.hideWindow(WindowConfigs.ClientMainWindow);
        appWindows.showWindow(WindowConfigs.ClientGetTicketWindow);
    }

    public void personalCabinetBtnPressed() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.createWindow(WindowConfigs.ClientCabinetWindow);
        appWindows.showWindow(WindowConfigs.ClientCabinetWindow);
        appWindows.reloadWindow(WindowConfigs.ClientCabinetWindow);
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
            int myId = ticket.getClientId();
            if (myId == Session.getInstance().getPersonId()) {
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
        TableColumn<Ticket, String> doctorColumn = new TableColumn<>("Doctor");
        doctorColumn.setMinWidth(100);
        doctorColumn.setCellValueFactory(new DoctorStringFactory());

        allTicketsTable.getColumns().add(doctorColumn);


        TableColumn<Ticket, String> activeColumn = new TableColumn<>("Status");
        activeColumn.setMinWidth(100);
        activeColumn.setCellValueFactory(new IsActiveStringFactory());

        allTicketsTable.getColumns().add(activeColumn);


        TableColumn<Ticket, GridPane> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setMinWidth(150);
        actionsColumn.setCellValueFactory(new ButtonsCellFactory());

        allTicketsTable.getColumns().add(actionsColumn);
    }

    private class DoctorStringFactory implements Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
            String doctor = param.getValue().getDoctorFirstName() + " " + param.getValue().getDoctorLastName();
            return new ReadOnlyStringWrapper(doctor);
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

            Button editBtn = new Button("edit");
            editBtn.setOnAction(new EditTicketEvent(ticket));

            Button deleteBtn = new Button("delete");
            deleteBtn.setOnAction(new DeleteTicketEvent(ticket.getId()));

            GridPane panel = new GridPane();
            panel.setHgap(2);
            panel.setVgap(2);
            panel.setPadding(new Insets(2, 2, 2, 2));

            panel.add(viewBtn, 0, 0);
            panel.add(editBtn, 1, 0);
            panel.add(deleteBtn, 2, 0);

            if (!ticket.getFinished()) {
                viewBtn.setDisable(true);
                deleteBtn.setDisable(false);
                editBtn.setDisable(false);
            } else {
                viewBtn.setDisable(false);
                deleteBtn.setDisable(true);
                editBtn.setDisable(true);
            }


            return new SimpleObjectProperty<>(panel);
        }
    }

    private class EditTicketEvent implements EventHandler<ActionEvent> {

        private Ticket editedTicket;

        public EditTicketEvent(Ticket ticket) {
            this.editedTicket = ticket;
        }

        @Override
        public void handle(ActionEvent event) {
            AppWindows appWindows = AppWindows.getInstance();

            appWindows.createWindow(WindowConfigs.ClientEditTicketWindow);
            try {
                appWindows.setFormObject(WindowConfigs.ClientEditTicketWindow, editedTicket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            appWindows.showWindow(WindowConfigs.ClientEditTicketWindow);
        }
    }

    private class DeleteTicketEvent implements EventHandler<ActionEvent> {

        private Integer ticketId;

        public DeleteTicketEvent(Integer ticketId) {
            this.ticketId = ticketId;
        }

        @Override
        public void handle(ActionEvent event) {
            TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
            try {
                ticketService.delete(ticketId);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                AppWindows.getInstance().reloadWindow(WindowConfigs.ClientMainWindow);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            appWindows.createWindow(WindowConfigs.ClientExploreTicketWindow);
            try {
                appWindows.setFormObject(WindowConfigs.ClientExploreTicketWindow, currentTicket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            appWindows.showWindow(WindowConfigs.ClientExploreTicketWindow);
        }
    }
}
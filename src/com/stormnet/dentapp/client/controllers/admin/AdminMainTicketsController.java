package com.stormnet.dentapp.client.controllers.admin;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
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
import java.util.List;
import java.util.ResourceBundle;

public class AdminMainTicketsController implements Controller<Ticket>, Initializable {

    @FXML
    private TableView<Ticket> allTicketsTable;

    @FXML
    public void allTicketsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.reloadWindow(WindowConfigs.AdminMainTicketsWindow);
    }

    @FXML
    public void allDoctorsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminMainTicketsWindow);
        appWindows.createWindow(WindowConfigs.AdminMainDoctorsWindow);
        appWindows.reloadWindow(WindowConfigs.AdminMainDoctorsWindow);
        appWindows.showWindow(WindowConfigs.AdminMainDoctorsWindow);
    }

    @FXML
    public void allClientsMenuSelected() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.AdminMainTicketsWindow);
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
        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        List<Ticket> allTickets = ticketService.loadAll();
        ObservableList<Ticket> gridItems = allTicketsTable.getItems();
        gridItems.clear();
        gridItems.addAll(allTickets);
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

    private class DoctorStringFactory implements Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
            String doctorName = param.getValue().getDoctorFirstName() + " " + param.getValue().getDoctorLastName();
            return new ReadOnlyStringWrapper(doctorName);
        }
    }

    private class ClientStringFactory implements Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>> {

        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
            String clientName = param.getValue().getClientFirstName() + " " + param.getValue().getClientLastName();
            return new ReadOnlyStringWrapper(clientName);
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

            appWindows.createWindow(WindowConfigs.AdminExploreTicketWindow);
            try {
                appWindows.setFormObject(WindowConfigs.AdminExploreTicketWindow, currentTicket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            appWindows.showWindow(WindowConfigs.AdminExploreTicketWindow);
        }
    }
}
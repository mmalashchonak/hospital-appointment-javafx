package com.stormnet.dentapp.client.controllers.client;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.Session;
import com.stormnet.dentapp.client.common.WindowConfigs;
import com.stormnet.dentapp.bo.Client;
import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.PersonService;
import com.stormnet.dentapp.clientservice.TicketService;
import com.stormnet.dentapp.clientservice.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientGetTicketController implements Controller, Initializable {

    @FXML
    private ComboBox VisitTime;

    @FXML
    private ComboBox<Doctor> ChooseDoctor;

    @FXML
    private DatePicker ChooseDate;

    @FXML
    public void timeListGeneration() throws IOException {

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        List<Ticket> allTickets = ticketService.loadAll();
        List<Ticket> allTicketsOfThisDoctor = new ArrayList<>();

        for (Ticket ticket : allTickets) {
            int doctorID = ticket.getDoctorId();
            if (doctorID == ChooseDoctor.getValue().getId()) {
                allTicketsOfThisDoctor.add(ticket);
            }
        }

        List<Ticket> allTicketsOfThisDoctorForThisDate = new ArrayList<>();

        for (Ticket ticket : allTicketsOfThisDoctor) {
            LocalDate date = ticket.getDate();
            if (date.equals(ChooseDate.getValue())) {
                allTicketsOfThisDoctorForThisDate.add(ticket);
            }
        }

        ObservableList<String> allTimesAvailable = FXCollections.observableArrayList();

        for (int i = 8; i < 18; i++) {
            allTimesAvailable.add(i + ":00");
        }

        for (Ticket ticket : allTicketsOfThisDoctorForThisDate) {
            String time = ticket.getTime();
            allTimesAvailable.remove(time);
        }

        VisitTime.setItems(allTimesAvailable);

    }

    @FXML
    public void saveBtnPressed() throws IOException {
        if (ChooseDoctor.getValue() == null || VisitTime.getValue() == null || ChooseDate.getValue() == null) {
            throw new RuntimeException("Please, fill all parameters.");
        }

        Integer clientId = Session.getInstance().getPersonId();
        Client client = new Client();
        try {
            client = ServiceFactory.getServiceFactory().getClientService().loadById(clientId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Ticket ticket = new Ticket();
        ticket.setClientId(clientId);
        ticket.setClientFirstName(client.getFirstName());
        ticket.setClientLastName(client.getLastName());
        ticket.setDoctorId(ChooseDoctor.getValue().getId());
        ticket.setDoctorLastName(ChooseDoctor.getValue().getLastName());
        ticket.setDoctorFirstName(ChooseDoctor.getValue().getFirstName());
        ticket.setDate(ChooseDate.getValue());
        ticket.setTime(VisitTime.getValue().toString());
        ticket.setCabinet(ChooseDoctor.getValue().getCabinet());
        ticket.setRating("");
        ticket.setDoctorComment("");
        ticket.setClientComment("");

        TicketService ticketService = ServiceFactory.getServiceFactory().getTicketService();
        ticketService.save(ticket);

        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.ClientGetTicketWindow);
        appWindows.reloadWindow(WindowConfigs.ClientMainWindow);
        appWindows.showWindow(WindowConfigs.ClientMainWindow);
    }

    @FXML
    public void cancelBtnPressed() throws IOException {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.ClientGetTicketWindow);
        appWindows.reloadWindow(WindowConfigs.ClientMainWindow);
    }

    @Override
    public void reloadWindow() throws IOException {
        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        List<Doctor> allPersons = doctorService.loadAll();

        ObservableList<Doctor> gridItems = ChooseDoctor.getItems();
        gridItems.clear();
        gridItems.addAll(allPersons);
    }

    @Override
    public void setFormObject(Object object) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChooseDoctor.getSelectionModel().selectFirst();
        ChooseDoctor.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {
            @Override
            public ListCell<Doctor> call(ListView<Doctor> l) {
                return new ListCell<Doctor>() {
                    @Override
                    protected void updateItem(Doctor doctor, boolean empty) {
                        super.updateItem(doctor, empty);
                        if (doctor == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(doctor.getFirstName() + " " + doctor.getLastName());
                        }
                    }
                };
            }
        });
        //selected value showed in combo box
        ChooseDoctor.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doctor) {
                if (doctor == null) {
                    return null;
                } else {
                    return doctor.getFirstName() + " " + doctor.getLastName();
                }
            }

            @Override
            public Doctor fromString(String id) {
                return null;
            }
        });

        ChooseDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.isBefore(LocalDate.now()));
            }
        });
        ChooseDate.setEditable(false);

        PersonService<Doctor> doctorService = ServiceFactory.getServiceFactory().getDoctorService();
        List<Doctor> allPersons = new ArrayList<>();
        try {
            allPersons = doctorService.loadAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Doctor> all = FXCollections.observableArrayList(allPersons);
        ChooseDoctor.setItems(all);
    }
}

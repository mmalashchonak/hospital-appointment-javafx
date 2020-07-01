package com.stormnet.dentapp.client.common;

public enum WindowConfigs {

    AdminCabinetWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-cabinet.fxml",
            "Admin Cabinet",
            true,
            700,
            275),

    AdminExploreClientTicketsWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-explore-client-tickets-window.fxml",
            "Tickets Of This Client Window",
            true,
            700,
            500),

    AdminExploreDoctorTicketsWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-explore-doctor-tickets-window.fxml",
            "Tickets Of This Doctor Window",
            true,
            600,
            500),

    AdminExploreTicketWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-explore-ticket-window.fxml",
            "Ticket Window",
            true,
            630,
            500),

    AdminMainTicketsWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-main-tickets.fxml",
            "Admin Main Window",
            true,
            700,
            300),

    AdminMainClientsWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-main-clients.fxml",
            "Admin Main Window",
            true,
            430,
            300),

    AdminMainDoctorsWindow("/com/stormnet/dentapp/client/xmlui/admin/admin-main-doctors.fxml",
            "Admin Main Window",
            true,
            580,
            275),

    ClientCabinetWindow("/com/stormnet/dentapp/client/xmlui/client/client-cabinet.fxml",
            "Client Cabinet",
            true,
            300,
            275),

    ClientEditTicketWindow("/com/stormnet/dentapp/client/xmlui/client/client-edit-ticket.fxml",
            "Edit Ticket",
            true,
            270,
            275),

    ClientExploreTicketWindow("/com/stormnet/dentapp/client/xmlui/client/client-explore-ticket-window.fxml",
            "Ticket Window",
            true,
            630,
            500),

    ClientGetTicketWindow("/com/stormnet/dentapp/client/xmlui/client/client-get-ticket.fxml",
            "Get Ticket",
            true,
            270,
            275),

    ClientMainWindow("/com/stormnet/dentapp/client/xmlui/client/client-main.fxml",
            "Client Main Window",
            true,
            700,
            300),

    DoctorCabinetWindow("/com/stormnet/dentapp/client/xmlui/doctor/doctor-cabinet.fxml",
            "Doctor Cabinet",
            true,
            260,
            300),

    DoctorExploreTicketWindow("/com/stormnet/dentapp/client/xmlui/doctor/doctor-explore-ticket-window.fxml",
            "Ticket Window",
            true,
            630,
            500),

    DoctorMainWindow("/com/stormnet/dentapp/client/xmlui/doctor/doctor-main.fxml",
            "Doctor Main Window",
            true,
            600,
            275),

    AdminLoginWindow("/com/stormnet/dentapp/client/xmlui/registration/admin-login-window.fxml",
            "Sign in",
            true,
            300,
            275),

    AdminRegistrationWindow("/com/stormnet/dentapp/client/xmlui/registration/admin-registration-window.fxml",
            "Registration",
            true,
            300,
            275),

    ClientLoginWindow("/com/stormnet/dentapp/client/xmlui/registration/client-login-window.fxml",
            "Sign in",
            true,
            300,
            275),

    ClientRegistrationWindow("/com/stormnet/dentapp/client/xmlui/registration/client-registration-window.fxml",
            "Registration",
            true,
            300,
            275),

    DoctorLoginWindow("/com/stormnet/dentapp/client/xmlui/registration/doctor-login-window.fxml",
            "Sign in",
            true,
            300,
            275),

    DoctorRegistrationWindow("/com/stormnet/dentapp/client/xmlui/registration/doctor-registration-window.fxml",
            "Registration",
            true,
            300,
            275),

    InvitationWindow("/com/stormnet/dentapp/client/xmlui/registration/invitation-window.fxml",
            "Invitation Window",
            false,
            300,
            100),

    WrongLoginPasswordWindow("/com/stormnet/dentapp/client/xmlui/registration/wrong-login-password.fxml",
            "Authorization Failed!",
            true,
            300,
            100),

    AboutWindow("/com/stormnet/dentapp/client/xmlui/about.fxml",
            "About",
            true,
            400,
            175);

    private String xmlUi;

    private String title;

    private boolean modalWindow;

    private int width;

    private int height;

    WindowConfigs(String xmlUi, String title, boolean modalWindow, int width, int height) {
        this.xmlUi = xmlUi;
        this.title = title;
        this.modalWindow = modalWindow;
        this.width = width;
        this.height = height;
    }

    public String getXmlUi() {
        return xmlUi;
    }

    public String getTitle() {
        return title;
    }

    public boolean isModalWindow() {
        return modalWindow;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

package com.stormnet.dentapp.web.impl;

import com.stormnet.dentapp.web.common.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final CommandFactory instance = new CommandFactory();

    private Map<String, Command> commands = new HashMap<>();

    public static CommandFactory get() {
        return instance;
    }

    public CommandFactory() {
        commands.put("get-all-tickets", new GetAllTicketsCommand());
        commands.put("save-ticket", new SaveTicketCommand());
        commands.put("get-ticket", new GetTicketCommand());
        commands.put("delete-ticket", new DeleteTicketCommand());
        commands.put("update-ticket", new UpdateTicketCommand());

        commands.put("get-client-by-login-and-password", new GetClientByLoginPasswordCommand());
        commands.put("get-all-clients", new GetAllClientsCommand());
        commands.put("save-client", new SaveClientCommand());
        commands.put("get-client", new GetClientCommand());
        commands.put("delete-client", new DeleteClientCommand());
        commands.put("update-client", new UpdateClientCommand());

        commands.put("get-doctor-by-login-and-password", new GetDoctorByLoginPasswordCommand());
        commands.put("get-all-doctors", new GetAllDoctorsCommand());
        commands.put("save-doctor", new SaveDoctorCommand());
        commands.put("get-doctor", new GetDoctorCommand());
        commands.put("delete-doctor", new DeleteDoctorCommand());
        commands.put("update-doctor", new UpdateDoctorCommand());

        commands.put("get-admin-by-login-and-password", new GetAdminByLoginPasswordCommand());
        commands.put("get-all-admins", new GetAllAdminsCommand());
        commands.put("save-admin", new SaveAdminCommand());
        commands.put("get-admin", new GetAdminCommand());
        commands.put("delete-admin", new DeleteAdminCommand());
        commands.put("update-admin", new UpdateAdminCommand());
    }

    public Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        return command;
    }
}

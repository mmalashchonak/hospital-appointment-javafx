package com.stormnet.dentapp.clientservice.impl;

import com.stormnet.dentapp.bo.Ticket;
import com.stormnet.dentapp.clientservice.TicketService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    public TicketServiceImpl() {

    }

    @Override
    public void save(Ticket ticket) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("save-ticket");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("isFinished").value(String.valueOf(ticket.getFinished()));
        jsonWriter.key("clientId").value(String.valueOf(ticket.getClientId()));
        jsonWriter.key("doctorLastName").value(ticket.getDoctorLastName());
        jsonWriter.key("doctorFirstName").value(ticket.getDoctorFirstName());
        jsonWriter.key("clientLastName").value(ticket.getClientLastName());
        jsonWriter.key("clientFirstName").value(ticket.getClientFirstName());
        jsonWriter.key("date").value((ticket.getDate().toString()));
        jsonWriter.key("time").value(ticket.getTime());
        jsonWriter.key("doctorId").value(String.valueOf(ticket.getDoctorId()));
        jsonWriter.key("cabinet").value(ticket.getCabinet());
        jsonWriter.key("rating").value(ticket.getRating());
        jsonWriter.key("clientComment").value(ticket.getClientComment());
        jsonWriter.key("doctorComment").value(ticket.getDoctorComment());

        jsonWriter.endObject();
        jsonWriter.endObject();

        writer.flush();

        InputStream is = socket.getInputStream();
        JSONTokener tokener = new JSONTokener(is);

        JSONObject response = (JSONObject) tokener.nextValue();
        JSONObject headers = response.getJSONObject("headers");

        int code = headers.getInt("status-code");
        String message = headers.getString("status-message");

        System.out.println(code + " - " + message);

        writer.close();
        is.close();
        socket.close();
    }


    @Override
    public List<Ticket> loadAll() throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-tickets");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.endObject();
        jsonWriter.endObject();

        writer.flush();

        InputStream is = socket.getInputStream();
        JSONTokener tokener = new JSONTokener(is);

        JSONObject response = (JSONObject) tokener.nextValue();
        JSONObject headers = response.getJSONObject("headers");

        int code = headers.getInt("status-code");
        String message = headers.getString("status-message");

        System.out.println(code + " - " + message);

        List<Ticket> allTickets = new ArrayList<>();
        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            int length = responseData.length();

            for (int i = 0; i < length; i++) {
                JSONObject object = (JSONObject) responseData.get(i);

                String IdStr = (String) object.get("id");
                Integer id = Integer.valueOf(IdStr);

                String clientIdStr = (String) object.get("clientId");
                Integer clientId = Integer.valueOf(clientIdStr);

                String isFinishedStr = (String) object.get("isFinished");
                Boolean isFinished = Boolean.valueOf(isFinishedStr);

                String doctorLastNameStr = (String) object.get("doctorLastName");

                String doctorFirstNameStr = (String) object.get("doctorFirstName");

                String clientLastNameStr = (String) object.get("clientLastName");

                String clientFirstNameStr = (String) object.get("clientFirstName");

                String dateStr = (String) object.get("date");
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                LocalDate date = LocalDate.parse(dateStr, formatter);

                String timeStr = (String) object.get("time");

                String doctorIdStr = (String) object.get("doctorId");
                Integer doctorId = Integer.valueOf(doctorIdStr);

                String cabinetStr = (String) object.get("cabinet");

                String ratingStr = (String) object.get("rating");

                String clientCommentStr = (String) object.get("clientComment");

                String doctorCommentStr = (String) object.get("doctorComment");

                Ticket ticket = new Ticket();
                ticket.setId(id);
                ticket.setFinished(isFinished);
                ticket.setClientId(clientId);
                ticket.setDoctorLastName(doctorLastNameStr);
                ticket.setDoctorFirstName(doctorFirstNameStr);
                ticket.setClientLastName(clientLastNameStr);
                ticket.setClientFirstName(clientFirstNameStr);
                ticket.setDate(date);
                ticket.setTime(timeStr);
                ticket.setDoctorId(doctorId);
                ticket.setCabinet(cabinetStr);
                ticket.setRating(ratingStr);
                ticket.setClientComment(clientCommentStr);
                ticket.setDoctorComment(doctorCommentStr);

                allTickets.add(ticket);
            }
        }
        writer.close();
        is.close();
        socket.close();
        return allTickets;
    }

    @Override
    public Ticket loadById(Integer id) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-ticket");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("id").value(String.valueOf(id));

        jsonWriter.endObject();

        jsonWriter.endObject();

        writer.flush();

        InputStream is = socket.getInputStream();
        JSONTokener tokener = new JSONTokener(is);

        JSONObject response = (JSONObject) tokener.nextValue();
        JSONObject headers = response.getJSONObject("headers");

        int code = headers.getInt("status-code");
        String message = headers.getString("status-message");

        System.out.println(code + " - " + message);

        Ticket ticket = new Ticket();

        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            JSONObject object = (JSONObject) responseData.get(0);

            String clientIdStr = (String) object.get("clientId");
            Integer clientId = Integer.valueOf(clientIdStr);

            String isFinishedStr = (String) object.get("isFinished");
            Boolean isFinished = Boolean.valueOf(isFinishedStr);

            String doctorLastNameStr = (String) object.get("doctorLastName");

            String doctorFirstNameStr = (String) object.get("doctorFirstName");

            String clientLastNameStr = (String) object.get("clientLastName");

            String clientFirstNameStr = (String) object.get("clientFirstName");

            String dateStr = (String) object.get("date");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate date = LocalDate.parse(dateStr, formatter);

            String timeStr = (String) object.get("time");

            String doctorIdStr = (String) object.get("doctorId");
            Integer doctorId = Integer.valueOf(doctorIdStr);

            String cabinetStr = (String) object.get("cabinet");

            String ratingStr = (String) object.get("rating");

            String clientCommentStr = (String) object.get("clientComment");

            String doctorCommentStr = (String) object.get("doctorComment");

            ticket.setId(id);
            ticket.setFinished(isFinished);
            ticket.setClientId(clientId);
            ticket.setDoctorLastName(doctorLastNameStr);
            ticket.setDoctorFirstName(doctorFirstNameStr);
            ticket.setClientLastName(clientLastNameStr);
            ticket.setClientFirstName(clientFirstNameStr);
            ticket.setDate(date);
            ticket.setTime(timeStr);
            ticket.setDoctorId(doctorId);
            ticket.setCabinet(cabinetStr);
            ticket.setRating(ratingStr);
            ticket.setClientComment(clientCommentStr);
            ticket.setDoctorComment(doctorCommentStr);

        }
        writer.close();
        is.close();
        socket.close();
        return ticket;
    }

    @Override
    public void delete(Integer id) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("delete-ticket");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("id").value(String.valueOf(id));

        jsonWriter.endObject();

        jsonWriter.endObject();

        writer.flush();

        InputStream is = socket.getInputStream();
        JSONTokener tokener = new JSONTokener(is);

        JSONObject response = (JSONObject) tokener.nextValue();
        JSONObject headers = response.getJSONObject("headers");

        int code = headers.getInt("status-code");
        String message = headers.getString("status-message");

        System.out.println(code + " - " + message);

        writer.close();
        is.close();
        socket.close();
    }

    @Override
    public void update(Ticket ticket) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("update-ticket");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("id").value(String.valueOf(ticket.getId()));
        jsonWriter.key("isFinished").value(String.valueOf(ticket.getFinished()));
        jsonWriter.key("clientId").value(String.valueOf(ticket.getClientId()));
        jsonWriter.key("doctorLastName").value(ticket.getDoctorLastName());
        jsonWriter.key("doctorFirstName").value(ticket.getDoctorFirstName());
        jsonWriter.key("clientLastName").value(ticket.getClientLastName());
        jsonWriter.key("clientFirstName").value(ticket.getClientFirstName());
        jsonWriter.key("date").value((ticket.getDate().toString()));
        jsonWriter.key("time").value(ticket.getTime());
        jsonWriter.key("doctorId").value(String.valueOf(ticket.getDoctorId()));
        jsonWriter.key("cabinet").value(ticket.getCabinet());
        jsonWriter.key("rating").value(ticket.getRating());
        jsonWriter.key("clientComment").value(ticket.getClientComment());
        jsonWriter.key("doctorComment").value(ticket.getDoctorComment());

        jsonWriter.endObject();
        jsonWriter.endObject();

        writer.flush();

        InputStream is = socket.getInputStream();
        JSONTokener tokener = new JSONTokener(is);

        JSONObject response = (JSONObject) tokener.nextValue();
        JSONObject headers = response.getJSONObject("headers");

        int code = headers.getInt("status-code");
        String message = headers.getString("status-message");

        System.out.println(code + " - " + message);

        writer.close();
        is.close();
        socket.close();
    }
}

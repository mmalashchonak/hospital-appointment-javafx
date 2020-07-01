package com.stormnet.dentapp.clientservice.impl;

import com.stormnet.dentapp.bo.Doctor;
import com.stormnet.dentapp.clientservice.PersonService;
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
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceImpl implements PersonService<Doctor> {


    public DoctorServiceImpl() {

    }

    @Override
    public void save(Doctor person) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("save-doctor");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("lastName").value(person.getLastName());
        jsonWriter.key("firstName").value(person.getFirstName());
        jsonWriter.key("login").value(person.getLogin());
        jsonWriter.key("password").value(person.getPassword());
        jsonWriter.key("cabinet").value(person.getCabinet());

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
    public List<Doctor> loadAll() throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-doctors");
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

        List<Doctor> allPersons = new ArrayList<>();
        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            int length = responseData.length();

            for (int i = 0; i < length; i++) {
                JSONObject object = (JSONObject) responseData.get(i);

                String IdStr = (String) object.get("id");
                Integer id = Integer.valueOf(IdStr);

                String lastNameStr = (String) object.get("lastName");

                String firstNameStr = (String) object.get("firstName");

                String loginStr = (String) object.get("login");

                String passwordStr = (String) object.get("password");

                String cabinetStr = (String) object.get("cabinet");

                Doctor person = new Doctor();
                person.setId(id);
                person.setFirstName(firstNameStr);
                person.setLastName(lastNameStr);
                person.setLogin(loginStr);
                person.setPassword(passwordStr);
                person.setCabinet(cabinetStr);

                allPersons.add(person);
            }
        }
        writer.close();
        is.close();
        socket.close();
        return allPersons;
    }

    @Override
    public Doctor loadById(Integer id) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-doctor");
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

        Doctor person = new Doctor();

        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            JSONObject object = (JSONObject) responseData.get(0);

            String lastNameStr = (String) object.get("lastName");

            String firstNameStr = (String) object.get("firstName");

            String loginStr = (String) object.get("login");

            String passwordStr = (String) object.get("password");

            String cabinetStr = (String) object.get("cabinet");

            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setLogin(loginStr);
            person.setPassword(passwordStr);
            person.setCabinet(cabinetStr);

        }
        writer.close();
        is.close();
        socket.close();
        return person;
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
        jsonWriter.key("command-name").value("delete-doctor");
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
    public void update(Doctor person) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("update-doctor");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("id").value(person.getId().toString());
        jsonWriter.key("lastName").value(person.getLastName());
        jsonWriter.key("firstName").value(person.getFirstName());
        jsonWriter.key("login").value(person.getLogin());
        jsonWriter.key("password").value(person.getPassword());
        jsonWriter.key("cabinet").value(person.getCabinet());

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
    public Doctor loadPersonByLoginAndPassword(String login, String password) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-doctor-by-login-and-password");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("login").value(login);
        jsonWriter.key("password").value(password);

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

        Doctor person = new Doctor();

        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            JSONObject object = (JSONObject) responseData.get(0);

            String idStr = (String) object.get("id");
            Integer id = Integer.valueOf(idStr);

            String lastNameStr = (String) object.get("lastName");

            String firstNameStr = (String) object.get("firstName");

            String loginStr = (String) object.get("login");

            String passwordStr = (String) object.get("password");

            String cabinetStr = (String) object.get("cabinet");

            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setLogin(loginStr);
            person.setPassword(passwordStr);
            person.setCabinet(cabinetStr);

        }
        writer.close();
        is.close();
        socket.close();
        return person;
    }
}
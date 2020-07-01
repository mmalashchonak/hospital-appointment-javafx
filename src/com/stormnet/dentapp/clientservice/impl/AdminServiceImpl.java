package com.stormnet.dentapp.clientservice.impl;

import com.stormnet.dentapp.bo.Admin;
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

public class AdminServiceImpl implements PersonService<Admin> {


    public AdminServiceImpl() {

    }

    @Override
    public void save(Admin person) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("save-admin");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("lastName").value(person.getLastName());
        jsonWriter.key("firstName").value(person.getFirstName());
        jsonWriter.key("login").value(person.getLogin());
        jsonWriter.key("password").value(person.getPassword());

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
    public List<Admin> loadAll() throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-admins");
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

        List<Admin> allPersons = new ArrayList<>();
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

                Admin person = new Admin();
                person.setId(id);
                person.setFirstName(firstNameStr);
                person.setLastName(lastNameStr);
                person.setLogin(loginStr);
                person.setPassword(passwordStr);

                allPersons.add(person);
            }
        }
        writer.close();
        is.close();
        socket.close();
        return allPersons;
    }

    @Override
    public Admin loadById(Integer id) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-admin");
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

        Admin person = new Admin();

        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            JSONObject object = (JSONObject) responseData.get(0);

            String lastNameStr = (String) object.get("lastName");

            String firstNameStr = (String) object.get("firstName");

            String loginStr = (String) object.get("login");

            String passwordStr = (String) object.get("password");

            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setLogin(loginStr);
            person.setPassword(passwordStr);

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
        jsonWriter.key("command-name").value("delete-admin");
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
    public void update(Admin person) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("update-admin");
        jsonWriter.endObject();

        jsonWriter.key("parameters");
        jsonWriter.object();

        jsonWriter.key("id").value(person.getId().toString());
        jsonWriter.key("lastName").value(person.getLastName());
        jsonWriter.key("firstName").value(person.getFirstName());
        jsonWriter.key("login").value(person.getLogin());
        jsonWriter.key("password").value(person.getPassword());

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
    public Admin loadPersonByLoginAndPassword(String login, String password) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(address, 1111);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONWriter jsonWriter = new JSONWriter(writer);

        jsonWriter.object();
        jsonWriter.key("headers");

        jsonWriter.object();
        jsonWriter.key("command-name").value("get-admin-by-login-and-password");
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

        Admin person = new Admin();

        if (code == 200) {

            JSONArray responseData = response.getJSONArray("response-bo");
            JSONObject object = (JSONObject) responseData.get(0);

            String idStr = (String) object.get("id");
            Integer id = Integer.valueOf(idStr);

            String lastNameStr = (String) object.get("lastName");

            String firstNameStr = (String) object.get("firstName");

            String loginStr = (String) object.get("login");

            String passwordStr = (String) object.get("password");

            person.setId(id);
            person.setFirstName(firstNameStr);
            person.setLastName(lastNameStr);
            person.setLogin(loginStr);
            person.setPassword(passwordStr);

        }
        writer.close();
        is.close();
        socket.close();
        return person;
    }
}
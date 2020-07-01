package com.stormnet.dentapp.web.socket;

import com.stormnet.dentapp.web.common.Request;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SocketJsonRequest implements Request {

    private String commandName;

    private Map<String, Object> parameters = new HashMap<>();

    public SocketJsonRequest(JSONTokener clientJson) {
        JSONObject clientRequest = (JSONObject) clientJson.nextValue();

        JSONObject clientHeaders = clientRequest.getJSONObject("headers");
        this.commandName = clientHeaders.getString("command-name");

        JSONObject clientParameters = clientRequest.getJSONObject("parameters");
        Set<String> paramNames = clientParameters.keySet();
        for (String paramName : paramNames) {
            Object paramValue = clientParameters.get(paramName);
            parameters.put(paramName, paramValue);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public Object getParameter(String paramName) {
        return parameters.get(paramName);
    }
}

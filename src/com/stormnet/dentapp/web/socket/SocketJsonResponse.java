package com.stormnet.dentapp.web.socket;

import com.stormnet.dentapp.web.common.Response;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketJsonResponse implements Response {

    private int statusCode;

    private String statusMessage;

    private List<Map<String, Object>> responseData = new ArrayList<>();

    private JSONWriter writer;

    public SocketJsonResponse() {
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setResponseCode(ResponseCode code) {
        this.statusCode = code.getCode();
        this.statusMessage = code.getMessage();
    }

    @Override
    public void addResponseData(Map<String, Object> data) {
        this.responseData.add(data);
    }

    @Override
    public List<Map<String, Object>> getResponseDataValue() {
        return responseData;
    }

    @Override
    public JSONWriter getJsonWriter() {
        return writer;
    }

    @Override
    public void setJsonWriter(JSONWriter writer) {
        this.writer = writer;
    }
}

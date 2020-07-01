package com.stormnet.dentapp.web.socket;

public enum ResponseCode {

    OkCode (200, "OK"),

    BadRequestCode (400, "Bad Request"),

    NotFoundCode (404, "Not Found"),

    ServerErrorCode (500, "Internal Server Error");

    private int code;

    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

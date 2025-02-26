package com.example.RestAPI.model;

import java.util.Date;


public class ErrorResponse {

    private int status;
    private String message;
    private Date timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

}

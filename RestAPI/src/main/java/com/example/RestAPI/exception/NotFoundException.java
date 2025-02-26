package com.example.RestAPI.exception;

public abstract class NotFoundException extends Exception {

    public NotFoundException( String object, long id) {
        super(object+ " with id " + id + " not found");
    }

    public NotFoundException( String object, String arg, String value) {
        super(object+ " with " + arg + " '" + value + "' not found");
    }

}

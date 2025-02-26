package com.example.RestAPI.exception;


public class NotFoundUserException extends NotFoundException {

    public NotFoundUserException(long id) {
        super("User", id);
    }

    public NotFoundUserException(String arg, String value) {
        super("User", arg, value);
    }
}

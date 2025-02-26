package com.example.RestAPI.exception;

public class NotFoundItemException extends NotFoundException {

    public NotFoundItemException(long id) {
        super("item", id);

    }

    public NotFoundItemException(String arg, String value) {
        super("item", arg, value);
    }

}

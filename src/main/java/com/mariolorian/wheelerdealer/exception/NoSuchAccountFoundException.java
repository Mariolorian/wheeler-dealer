package com.mariolorian.wheelerdealer.exception;

public class NoSuchAccountFoundException extends RuntimeException {

    public NoSuchAccountFoundException() {
        super("There is no account registered with a given id");
    }

}

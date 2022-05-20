package com.mariolorian.wheelerdealer.exception;

public class NotEnoughFoundsException extends RuntimeException {

    public NotEnoughFoundsException() {
        super("You dont have enough founds to exchange");
    }

}

package com.mariolorian.wheelerdealer.exception;

public class UserWithSuchPeselAlreadyExistedException extends RuntimeException {

    public UserWithSuchPeselAlreadyExistedException() {
        super("User with a given PESEL is already registered");
    }

}

package com.mariolorian.wheelerdealer.exception;

public class NbpExchangeServiceOfflineException extends RuntimeException {

    public NbpExchangeServiceOfflineException() {
        super("Service is not available now. Try to exchange later again");
    }

}

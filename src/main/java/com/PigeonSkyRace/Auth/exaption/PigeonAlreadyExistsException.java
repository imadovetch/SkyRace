package com.PigeonSkyRace.Auth.exaption;

public class PigeonAlreadyExistsException extends RuntimeException {
    public PigeonAlreadyExistsException(String message) {
        super(message);
    }
}

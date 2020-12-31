package com.udacity.jdnd.course3.critter.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(){

    }

    public CustomerNotFoundException(String message){
        super(message);
    }
}

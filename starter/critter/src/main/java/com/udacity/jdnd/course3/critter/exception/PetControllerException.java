package com.udacity.jdnd.course3.critter.exception;

public class PetControllerException extends RuntimeException {
    public PetControllerException(){

    }

    public PetControllerException(String message){
        super(message);
    }
}
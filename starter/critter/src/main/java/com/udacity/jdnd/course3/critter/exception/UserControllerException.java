package com.udacity.jdnd.course3.critter.exception;

public class UserControllerException extends RuntimeException {
    public UserControllerException(){

    }

    public UserControllerException(String message){
        super(message);
    }
}

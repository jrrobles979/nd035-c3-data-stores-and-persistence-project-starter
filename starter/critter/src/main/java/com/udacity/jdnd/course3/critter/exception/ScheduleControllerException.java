package com.udacity.jdnd.course3.critter.exception;

public class ScheduleControllerException extends RuntimeException {
    public ScheduleControllerException(){

    }

    public ScheduleControllerException(String message){
        super(message);
    }
}
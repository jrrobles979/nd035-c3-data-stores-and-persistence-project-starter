package com.udacity.jdnd.course3.critter.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(){

    }

    public ScheduleNotFoundException(String message){
        super(message);
    }
}
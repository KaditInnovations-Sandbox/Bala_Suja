package com.travelease.travelease.exception;

public class DuplicatePhoneNumberException extends RuntimeException{
     public DuplicatePhoneNumberException(String message){
        super(message);
     }
}

package com.louiskhanh.airbnb_clone_be.user.application;

public class UserException extends RuntimeException{
    public UserException(String message){
        super(message);
    }
}
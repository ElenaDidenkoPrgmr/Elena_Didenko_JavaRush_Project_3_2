package com.javarush.eldidenko.servlet_quest.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}

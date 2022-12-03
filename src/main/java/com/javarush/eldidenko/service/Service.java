package com.javarush.eldidenko.service;

import com.javarush.eldidenko.repository.Repository;
import com.javarush.eldidenko.service.exception.ServiceException;
import org.apache.logging.log4j.Logger;

public class Service<K, T> {
    protected final Repository<K, T> repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    protected int parsingStringToInt(String stringValue, String stringName, Logger LOGGER) {
        int result;
        try {
            result = Integer.parseInt(stringValue);
        } catch (RuntimeException exception) {
            String errorMsg = stringName + " is not integer " + exception;
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        return result;
    }
}

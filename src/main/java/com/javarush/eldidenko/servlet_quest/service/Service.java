package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;
import org.apache.logging.log4j.Logger;

public class Service<K, T> {
    final Repository<K, T> repository;

    public Service(Repository<K, T> repository) {
        this.repository = repository;
    }

    protected int parsingStringToInt(String stringValue, String stringName, Logger logger) {
        int result;
        try {
            result = Integer.parseInt(stringValue);
        } catch (RuntimeException exception) {
            var errorMsg = stringName + " is not integer " + exception;
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        return result;
    }
}

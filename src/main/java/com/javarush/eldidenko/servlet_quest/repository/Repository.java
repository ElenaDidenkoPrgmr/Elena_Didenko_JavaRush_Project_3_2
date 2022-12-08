package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.repository.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Repository<K, T> {
    private static final Logger LOGGER = LogManager.getLogger(Repository.class);
    private static final String MESSAGE_KEY_NULL_ERROR_IN_ADD =
            "key can't be null for add to repository operation";
    private static final String MESSAGE_KEY_NULL_ERROR_IN_GET =
            "key can't be null for getById operation";
    private static final String MESSAGE_VALUE_NULL_ERROR_IN_ADD =
            "value can;t be null";
    private static final String MESSAGE_KEY_NULL_ERROR_IN_CONTAINS =
            "key can't be null for containsId operation";

    protected Map<K, T> repository;

    public Repository(Map<K, T> repository) {
        this.repository = repository;
    }

    public void add(K key, T value) {
        if (key == null) {
            var errorMsg = MESSAGE_KEY_NULL_ERROR_IN_ADD;
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }

        if (value == null) {
            var errorMsg = MESSAGE_VALUE_NULL_ERROR_IN_ADD;
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }
        repository.put(key, value);
    }

    public T getById(K key) {
        if (key == null) {
            var errorMsg = MESSAGE_KEY_NULL_ERROR_IN_GET;
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }
        return repository.get(key);
    }

    public boolean containsId(K key) {
        if (key == null) {
            var errorMsg = MESSAGE_KEY_NULL_ERROR_IN_CONTAINS;
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }
        return repository.containsKey(key);
    }
}

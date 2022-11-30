package com.javarush.repository;

import com.javarush.repository.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Repository<K, T> {
    private static final Logger LOGGER = LogManager.getLogger(Repository.class);

    public Map<K, T> repository;

    public Repository(Map<K, T> repository) {
        this.repository = repository;
    }

    public void add(K key, T value) {
        if (key == null) {
            String errorMsg = "key can't be null for add to repository operation";
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }

        if (value == null) {
            String errorMsg = "value can;t be null";
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }

        repository.put(key, value);
    }

    public T getById(K key) {
        if (key == null) {
            String errorMsg = "key can't be null for getById operation";
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }

        return repository.get(key);
    }

    public boolean containsId(K key) {
        if (key == null) {
            String errorMsg = "key can't be null for containsId operation";
            LOGGER.error(errorMsg);
            throw new RepositoryException(errorMsg);
        }

        return repository.containsKey(key);
    }
}

package com.javarush.repository;

import java.util.HashMap;
import java.util.Map;

public abstract class Repository<K, T> {
    public Map<K, T> repository = new HashMap<>();

    public boolean isKeep(K key){
        return this.repository.containsKey(key);
    }

    public void add(K key, T value) {
        repository.put(key, value);
    }

    public T get(K key){
        return repository.get(key);
    }
}

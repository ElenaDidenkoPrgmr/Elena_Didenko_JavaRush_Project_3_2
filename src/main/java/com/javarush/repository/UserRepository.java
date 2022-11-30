package com.javarush.repository;

import com.javarush.entity.User;

import java.util.Map;

public class UserRepository extends Repository<String, User> {
    public UserRepository(Map<String, User> repository) {
        super(repository);
    }
}

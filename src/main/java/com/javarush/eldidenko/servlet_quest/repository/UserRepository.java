package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.User;

import java.util.Map;

public class UserRepository extends Repository<String, User> {
    public UserRepository(Map<String, User> repository) {
        super(repository);
    }
}

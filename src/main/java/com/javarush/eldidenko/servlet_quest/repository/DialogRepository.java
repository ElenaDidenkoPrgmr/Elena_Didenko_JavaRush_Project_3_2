package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.Dialog;

import java.util.Map;

public class DialogRepository extends Repository<Integer, Dialog> {
    public DialogRepository(Map<Integer, Dialog> repository) {
        super(repository);
    }
}

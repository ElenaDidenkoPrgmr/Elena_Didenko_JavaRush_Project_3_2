package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.Quest;

import java.util.Map;

public class QuestRepository extends Repository<Integer, Quest> {
    public QuestRepository(Map<Integer, Quest> repository) {
        super(repository);
    }
}

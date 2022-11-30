package com.javarush.repository;

import com.javarush.entity.Quest;

import java.util.Map;

public class QuestRepository extends Repository<Integer, Quest> {
    public QuestRepository(Map<Integer, Quest> repository) {
        super(repository);
    }
}

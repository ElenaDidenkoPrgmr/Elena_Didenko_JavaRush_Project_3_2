package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.Npc;

import java.util.Map;

public class NpcRepository extends Repository<Integer, Npc> {
    public NpcRepository(Map<Integer, Npc> repository) {
        super(repository);
    }
}

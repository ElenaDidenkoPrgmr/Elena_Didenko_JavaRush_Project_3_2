package com.javarush.eldidenko.repository;

import com.javarush.eldidenko.entity.Npc;

import java.util.Map;

public class NpcRepository extends Repository<Integer, Npc> {
    public NpcRepository(Map<Integer, Npc> repository) {
        super(repository);
    }
}

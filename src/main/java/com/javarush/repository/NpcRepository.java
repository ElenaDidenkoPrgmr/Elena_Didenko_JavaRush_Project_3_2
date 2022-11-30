package com.javarush.repository;

import com.javarush.entity.Npc;

import java.util.Map;

public class NpcRepository extends Repository<Integer, Npc> {
    public NpcRepository(Map<Integer, Npc> repository) {
        super(repository);
    }
}

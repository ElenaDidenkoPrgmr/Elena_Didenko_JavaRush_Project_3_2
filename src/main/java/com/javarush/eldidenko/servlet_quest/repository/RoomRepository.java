package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomRepository extends Repository<Integer, Room> {
    public RoomRepository(Map<Integer, Room> repository) {
        super(repository);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(repository.values());
    }
}

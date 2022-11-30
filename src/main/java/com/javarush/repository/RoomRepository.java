package com.javarush.repository;

import com.javarush.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomRepository extends Repository<Integer, Room> {
    public RoomRepository(Map repository) {
        super(repository);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(repository.values());
    }
}

package com.javarush.repository;

import com.javarush.dto.RoomDTO;
import com.javarush.entity.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRepository extends Repository<Integer, Room> {

    public List<RoomDTO> getNextRoomList(Integer roomId) {
        List<RoomDTO> result = new ArrayList<>();
        Room currentRoom = repository.get(roomId);

        return result;
    }
    public Map<Integer, String> getNextRoomMap(Integer roomId) {
        Map<Integer,String> result = new HashMap<>();
        Room currentRoom = repository.get(roomId);

        return result;
    }

    public List<Room> getRooms(){
        return new ArrayList<>(repository.values());
    }
}

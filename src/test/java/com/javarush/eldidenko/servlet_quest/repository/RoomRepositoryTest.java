package com.javarush.eldidenko.servlet_quest.repository;

import com.javarush.eldidenko.servlet_quest.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomRepositoryTest {
    private Map<Integer, Room> map;
    private RoomRepository repository;

    @BeforeEach
    void init() {
        Room roomOne = new Room();
        roomOne.setId(1);
        roomOne.setName("name");
        roomOne.setLevel(1);

        Room roomTwo = new Room();
        roomTwo.setId(2);
        roomTwo.setName("name2");
        roomTwo.setLevel(2);

        map = new HashMap<>();
        map.put(1, roomOne);
        map.put(2, roomTwo);
        this.repository = new RoomRepository(map);
    }

    @Test
    void test_getRooms_WhenRepositoryIsNotEmpty() {
        Room roomOne = new Room();
        roomOne.setId(1);
        roomOne.setName("name");
        roomOne.setLevel(1);

        Room roomTwo = new Room();
        roomTwo.setId(2);
        roomTwo.setName("name2");
        roomTwo.setLevel(2);

        List<Room> expected = new ArrayList<>(Arrays.asList(roomOne, roomTwo));
        List<Room> actual = repository.getRooms();

        assertEquals(expected, actual);
    }

    @Test
    void test_getRooms_WhenRepositoryIsEmpty() {
        List<Room> expected = new ArrayList<>();
        this.repository = new RoomRepository(new HashMap<>());
        assertEquals(expected, repository.getRooms());
    }
}

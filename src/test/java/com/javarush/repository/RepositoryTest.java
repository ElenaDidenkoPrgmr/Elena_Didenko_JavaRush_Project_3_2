package com.javarush.repository;

import com.javarush.entity.Room;
import com.javarush.repository.Repository;
import com.javarush.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {
    private Map<Integer, Room> map;
    private Repository<Integer, Room> repository;

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
        this.repository = new Repository<>(map);
    }

    @Test
    void test_add_ShouldThrowExceptionWhenKeyIsNull() {
        assertThrows(RepositoryException.class, () -> repository.add(1, null));
    }

    @Test
    void test_add_ShouldThrowExceptionWhenValueIsNull() {
        assertThrows(RepositoryException.class, () -> repository.add(null, new Room()));
    }

    @Test
    void test_add_ShouldThrowExceptionWhenBothArgumetsAreNull() {
        assertThrows(RepositoryException.class, () -> repository.add(null, null));
    }

    @Test
    void test_add_NewCorrectEntity() {
        Room newRoom = new Room();
        repository.add(3, newRoom);
        assertEquals(3, map.size());
        assertEquals(newRoom, map.get(3));
    }

    @Test
    void test_add_DuplicatedEntity() {
        Room newRoom = new Room();
        newRoom.setId(1);
        newRoom.setName("name");
        newRoom.setLevel(5);
        repository.add(2, newRoom);

        Room expectedRoom = new Room();
        expectedRoom.setId(1);
        expectedRoom.setName("name");
        expectedRoom.setLevel(5);

        assertEquals(2, map.size());
        assertEquals(expectedRoom, map.get(2));
    }

    @Test
    void test_getById_WhenKeyPresent() {
        Room expectedRoom = new Room();
        expectedRoom.setId(1);
        expectedRoom.setName("name");
        expectedRoom.setLevel(1);

        Room actualRoom = repository.getById(1);
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    void test_getById_WhenKeyNotPresent() {
        Room actualRoom = repository.getById(10);
        assertNull(actualRoom);
    }

    @Test
    void test_getById_ShouldThrowExceptionWhenKeyIsNull() {
        assertThrows(RepositoryException.class, () -> repository.getById(null));
    }

    @Test
    void test_containsId_WhenKeyPresent() {
        boolean actual = repository.containsId(1);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void test_containsId_WhenKeyNotPresent() {
        boolean actual = repository.containsId(10);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    void test_containsId_ShouldThrowExceptionWhenKeyIsNull() {
        assertThrows(RepositoryException.class, () -> repository.containsId(null));
    }
}

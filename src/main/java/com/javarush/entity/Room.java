package com.javarush.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Room implements Serializable {

    public static final int START_ROOM_ID = 1;
    public static final int START_LEVEL = 0;
    public static final int FINISH_ROOM_ID = 10;
    public static final int FINISH_LEVEL = 4;

    private int id;
    private String name;
    private int level;
    private List<Integer> npc = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && level == room.level && name.equals(room.name) && npc.equals(room.npc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, npc);
    }
}

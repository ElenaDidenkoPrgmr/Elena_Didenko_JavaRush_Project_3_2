package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Data
@NoArgsConstructor
public class Room {

    public static final int START_ROOM_ID = 1;
    public static final int START_LEVEL = 0;
    public static final int FINISH_ROOM_ID = 10;
    public static final int FINISH_LEVEL = 4;

    private int id;
    private String name;
    private int level;
    private List<Integer> npc = new ArrayList<>();
}

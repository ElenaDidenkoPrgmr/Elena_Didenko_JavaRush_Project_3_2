package com.javarush.eldidenko.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    public static final int START_TOTAL_GAME = 0;
    public static final int START_POINT = 0;

    private String name;
    private int totalGame;
    private int currentRoomId;
    private int level;
    private int point;
    private List<Integer> endedQuest;
}

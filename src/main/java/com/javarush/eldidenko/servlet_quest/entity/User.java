package com.javarush.eldidenko.servlet_quest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

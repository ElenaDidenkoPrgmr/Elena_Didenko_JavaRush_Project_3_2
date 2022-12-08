package com.javarush.eldidenko.servlet_quest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoomDTO {
    private int id;
    private String name;
    private int level;
}

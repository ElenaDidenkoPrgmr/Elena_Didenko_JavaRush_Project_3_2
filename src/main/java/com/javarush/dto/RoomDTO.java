package com.javarush.dto;
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
    private boolean open;
}

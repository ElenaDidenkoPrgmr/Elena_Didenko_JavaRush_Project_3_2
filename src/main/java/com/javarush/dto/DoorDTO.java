package com.javarush.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoorDTO {
    private int id;
    private String name;
}

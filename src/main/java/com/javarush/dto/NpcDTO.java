package com.javarush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NpcDTO {
    private Integer id;
    private String name;
    private String avatar;
    private String description;
}

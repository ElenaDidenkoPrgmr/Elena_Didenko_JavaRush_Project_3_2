package com.javarush.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Data
@NoArgsConstructor
public class Npc {
    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private Integer startMessageId;
}

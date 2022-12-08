package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Npc {
    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private Integer startMessageId;

    public NpcDTO getNpcDTO() {
        return NpcDTO.builder()
                .id(id)
                .name(name)
                .avatar(avatar)
                .description(description)
                .build();
    }
}

package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class AnswerToQuest {
    private int id;
    private String text;
    @JsonProperty
    private boolean isRight;
}

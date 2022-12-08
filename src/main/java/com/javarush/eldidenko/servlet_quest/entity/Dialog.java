package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Dialog {
    private int id;
    private String text;
    private List<AnswerToDialog> answers;
}

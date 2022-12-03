package com.javarush.eldidenko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnswerToQuestDTO {
    private int id;
    private String text;
}

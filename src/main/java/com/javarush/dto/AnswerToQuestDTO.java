package com.javarush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnswerToQuestDTO {
    private int id;
    String text;
}

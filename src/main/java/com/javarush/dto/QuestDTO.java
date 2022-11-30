package com.javarush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class QuestDTO {
    private String text;
    private List<AnswerToQuestDTO> answers;
}

package com.javarush.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javarush.dto.AnswerToQuestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Quest {
    private Integer id;
    private String text;
    private List<AnswerToQuest> answers;

    public List<AnswerToQuestDTO> getAnswerDTO() {
        List<AnswerToQuestDTO> result = new ArrayList<>();
        for (AnswerToQuest answer : answers) {
            AnswerToQuestDTO answerToQuestDTO = new AnswerToQuestDTO(answer.getId(), answer.getText());
            result.add(answerToQuestDTO);
        }
        return result;
    }

    public boolean isRightAnswer(int idAnswer) {
        boolean result = false;
        for (AnswerToQuest answer : answers) {
            if (answer.getId() == idAnswer) {
                result = answer.isRight();
                break;
            }
        }
        return result;
    }
}

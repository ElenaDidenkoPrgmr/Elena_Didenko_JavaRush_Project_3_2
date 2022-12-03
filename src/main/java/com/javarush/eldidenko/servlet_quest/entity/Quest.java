package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javarush.eldidenko.servlet_quest.dto.AnswerToQuestDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Data
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
        AnswerToQuest answer = answers.stream()
                .filter(x -> x.getId() == idAnswer)
                .findFirst()
                .orElse(null);
        return answer == null ? false : answer.isRight();
    }
}

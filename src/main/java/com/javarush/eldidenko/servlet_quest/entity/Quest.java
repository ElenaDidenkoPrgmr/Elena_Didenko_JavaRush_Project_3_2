package com.javarush.eldidenko.servlet_quest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javarush.eldidenko.servlet_quest.dto.AnswerToQuestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Quest {
    private Integer id;
    private String text;
    private List<AnswerToQuest> answers;

    public List<AnswerToQuestDTO> getAnswerDTO() {
        return answers.stream()
                .map(answer -> new AnswerToQuestDTO(answer.getId(), answer.getText()))
                .collect(Collectors.toList());
    }

    public boolean isRightAnswer(int idAnswer) {
        var currentAnswer = answers.stream()
                .filter(answer -> answer.getId() == idAnswer)
                .findFirst()
                .orElse(null);
        return currentAnswer == null ? false : currentAnswer.isRight();
    }
}

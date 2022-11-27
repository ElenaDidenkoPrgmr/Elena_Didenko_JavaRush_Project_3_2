package com.javarush.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class QuestionNpc implements Serializable {

    private int id;
    private String text;
    List<AnswerToNpc> answers;


    public String[] getAnswersFromQuestion(){
        String[] result = new String[this.getAnswers().size()];
        for (int i = 0; i < this.getAnswers().size(); i++) {
            result[i] = this.getAnswers().get(i).getText();
        }

        //result = question.getAnswers().toArray();*/
        return result;
        //   return list.toArray(new String[0])
    }

}



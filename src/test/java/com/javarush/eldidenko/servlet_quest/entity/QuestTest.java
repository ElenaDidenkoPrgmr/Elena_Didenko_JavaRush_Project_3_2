package com.javarush.eldidenko.servlet_quest.entity;

import com.javarush.eldidenko.servlet_quest.dto.AnswerToQuestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class QuestTest {
    private Quest testQuest;
    AnswerToQuest answer1, answer2, answer3;

    @BeforeEach
    void init() {
        AnswerToQuest answer1 = new AnswerToQuest();
        answer1.setId(1);
        answer1.setText("One");
        answer1.setRight(true);

        AnswerToQuest answer2 = new AnswerToQuest();
        answer2.setId(2);
        answer2.setText("Two");
        answer2.setRight(false);

        AnswerToQuest answer3 = new AnswerToQuest();
        answer3.setId(3);
        answer3.setText("Three");
        answer3.setRight(false);

        testQuest = new Quest();
        testQuest.setAnswers(Arrays.asList(answer1,answer2,answer3));
    }
    @Test
    void test_getAnswerDTO() {
        AnswerToQuestDTO answerToQuestDTO1 = AnswerToQuestDTO.builder()
                .id(1)
                .text("One")
                .build();

        AnswerToQuestDTO answerToQuestDTO2 = AnswerToQuestDTO.builder()
                .id(2)
                .text("Two")
                .build();

        AnswerToQuestDTO answerToQuestDTO3 = AnswerToQuestDTO.builder()
                .id(3)
                .text("Three")
                .build();

        List<AnswerToQuestDTO> expectedListDTO = Arrays.asList(answerToQuestDTO1,answerToQuestDTO2,answerToQuestDTO3);

        List<AnswerToQuestDTO> actualListDTO = testQuest.getAnswerDTO();
        assertEquals(expectedListDTO,actualListDTO);
    }

    @Test
    void test_isRightAnswer_WhenAnswerIsRight(){
        assertTrue(testQuest.isRightAnswer(1));
    }

    @Test
    void test_isRightAnswer_WhenAnswerIsWrong(){
        assertFalse(testQuest.isRightAnswer(3));
    }

    @Test
    void test_isRightAnswer_WhenAnswerNotPresent(){
        assertFalse(testQuest.isRightAnswer(5));
    }
}

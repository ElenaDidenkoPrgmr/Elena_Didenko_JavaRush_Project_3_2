package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.dto.QuestDTO;
import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.AnswerToQuest;
import com.javarush.eldidenko.servlet_quest.entity.Quest;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.service.QuestService;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {

   @Mock
    private Repository<Integer, Quest> questRepository;

    private QuestService questService;

    @BeforeEach
    void init(){
        questService = new QuestService(questRepository);
    }


    @Test
    void test_getQuestDTO_ShouldThrowException_WhenQuestIdValueIsNull() {
        assertThrows(ServiceException.class,
                () -> questService.getQuestDTO(null));
    }

    @Test
    void test_getQuestDTO() {
        Quest testQuest = new Quest();
        testQuest.setId(1);
        testQuest.setText("test");
        testQuest.setAnswers(new ArrayList<>());
        when(questRepository.getById(eq(1))).thenReturn(testQuest);

        QuestDTO expectedDTO = QuestDTO.builder()
                .text("test")
                .answers(new ArrayList<>())
                .build();
        QuestDTO actualDTO = questService.getQuestDTO("1");

        assertEquals(expectedDTO,actualDTO);
    }

    @Test
    void test_setUserLevelAndPoints() {
        User testUser = new User();
        testUser.setLevel(2);
        testUser.setPoint(30);

        RoomDTO endedRoom = Mockito.mock(RoomDTO.class);
        when(endedRoom.getLevel()).thenReturn(2);

        questService.setUserLevelAndPoints(testUser,endedRoom);

        assertEquals(3,testUser.getLevel());
        assertEquals(30 + endedRoom.getLevel() * 10,testUser.getPoint());
    }

    @Test
    void test_questIsSuccess_WhenAnswerIsRight() {
        Quest testQuest = new Quest();
        testQuest.setId(1);
        testQuest.setText("test");

        AnswerToQuest answer1 = new AnswerToQuest();
        answer1.setId(1);
        answer1.setRight(true);

        testQuest.setAnswers(Arrays.asList(answer1));

        when(questService.repository.getById(1)).thenReturn(testQuest);

        assertEquals(true, questService.questIsSuccess("1","1"));
    }

    @Test
    void test_questIsSuccess_WhenAnswerIsWrong() {
        Quest testQuest = new Quest();
        testQuest.setId(1);
        testQuest.setText("test");

        AnswerToQuest answer1 = new AnswerToQuest();
        answer1.setId(1);
        answer1.setRight(false);

        testQuest.setAnswers(Arrays.asList(answer1));

        when(questService.repository.getById(1)).thenReturn(testQuest);

        assertEquals(false, questService.questIsSuccess("1","1"));
    }

    @Test
    void closeRoom() {
        User testUser = new User();
        testUser.setEndedQuest(new ArrayList<>());

        RoomDTO endedRoom = Mockito.mock(RoomDTO.class);
        when(endedRoom.getId()).thenReturn(2);

        questService.closeRoom(testUser,endedRoom);
        assertEquals(true,testUser.getEndedQuest().contains(2));
    }
}

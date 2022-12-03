package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.AnswerToQuestDTO;
import com.javarush.eldidenko.servlet_quest.dto.QuestDTO;
import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.service.QuestService;
import com.javarush.eldidenko.servlet_quest.servlet.QuestServlet;
import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletConfig servletConfig;

    @Mock
    ServletContext context;

    @Mock
    QuestService questService;
    @Mock
    RequestDispatcher requestDispatcher;

    QuestServlet questServlet;

    @BeforeEach
    void setup() throws ServletException {
        when(servletConfig.getServletContext())
                .thenReturn(context);

        when(context.getAttribute(eq(QUEST_SERVICE.toString())))
                .thenReturn(questService);

        when(request.getSession())
                .thenReturn(session);

        questServlet = new QuestServlet();
        questServlet.init(servletConfig);
    }

    @Test
    void test_doGet_WhenQuestIdNotNull() throws ServletException, IOException {
        QuestDTO questDTO =QuestDTO.builder()
                .text("Some quest")
                .answers(List.of(AnswerToQuestDTO.builder()
                                .id(1)
                                .text("answer1")
                        .build(),
                        AnswerToQuestDTO.builder()
                                .id(2)
                                .text("answer2")
                                .build()))
                .build();
        when(session.getAttribute(QUEST_ID.toString())).thenReturn("1");
        when(questService.getQuestDTO("1")).thenReturn(questDTO);
        when(context.getRequestDispatcher(eq("/WEB-INF/quest.jsp")))
                .thenReturn(requestDispatcher);
        questServlet.doGet(request,response);
        verify(session,times(1)).setAttribute(QUEST_INFO.toString(), questDTO);
        verify(requestDispatcher, times(1))
                .forward(request, response);
    }

    @Test
    void test_doGet_WhenQuestIdNull() throws ServletException, IOException {
        QuestDTO questDTO =QuestDTO.builder()
                .text("Some quest")
                .answers(List.of(AnswerToQuestDTO.builder()
                                .id(1)
                                .text("answer1")
                                .build(),
                        AnswerToQuestDTO.builder()
                                .id(2)
                                .text("answer2")
                                .build()))
                .build();
        when(session.getAttribute(QUEST_ID.toString())).thenReturn(null);
        when(context.getRequestDispatcher(eq("/WEB-INF/quest.jsp")))
                .thenReturn(requestDispatcher);
        questServlet.doGet(request,response);
        verify(session,times(0)).setAttribute(QUEST_INFO.toString(), questDTO);
        verify(requestDispatcher, times(1))
                .forward(request, response);
    }

    @Test
    void test_doPost_WhenQuestIsSuccess() throws ServletException, IOException {
        User testUser = new User();
        RoomDTO roomDTO = RoomDTO.builder()
                .id(1)
                .level(2)
                .name("testRoom")
                .build();

        when(session.getAttribute(USER.toString())).thenReturn(testUser);
        when(session.getAttribute(QUEST_ID.toString())).thenReturn("3");
        when(request.getParameter(QUEST_ANSWER_ID.toString())).thenReturn("4");
        when(session.getAttribute(CURRENT_ROOM.toString())).thenReturn(roomDTO);
        when(questService.questIsSuccess("3", "4")).thenReturn(true);
        when(context.getRequestDispatcher(eq("/WEB-INF/questOver.jsp")))
                .thenReturn(requestDispatcher);

        questServlet.doPost(request,response);

        verify(questService,times(1)).setUserLevelAndPoints(testUser, roomDTO);
        verify(session,times(1)).setAttribute(RESULT_QUEST.toString(), true);
        verify(questService,times(1)).closeRoom(testUser, roomDTO);
        verify(requestDispatcher, times(1))
                .forward(request, response);
    }

    @Test
    void test_doPost_WhenQuestIsWrong() throws ServletException, IOException {
        User testUser = new User();
        RoomDTO roomDTO = RoomDTO.builder()
                .id(1)
                .level(2)
                .name("testRoom")
                .build();

        when(session.getAttribute(USER.toString())).thenReturn(testUser);
        when(session.getAttribute(QUEST_ID.toString())).thenReturn("3");
        when(request.getParameter(QUEST_ANSWER_ID.toString())).thenReturn("4");
        when(session.getAttribute(CURRENT_ROOM.toString())).thenReturn(roomDTO);
        when(questService.questIsSuccess("3", "4")).thenReturn(false);
        when(context.getRequestDispatcher(eq("/WEB-INF/questOver.jsp")))
                .thenReturn(requestDispatcher);

        questServlet.doPost(request,response);

        verify(questService,times(0)).setUserLevelAndPoints(testUser,roomDTO);
        verify(session,times(1)).setAttribute(RESULT_QUEST.toString(), false);
        verify(questService,times(1)).closeRoom(testUser, roomDTO);
        verify(requestDispatcher, times(1))
                .forward(request, response);
    }
}

package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.QuestDTO;
import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.User;

import com.javarush.eldidenko.servlet_quest.service.QuestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "questServlet", value = "/quest")
public class QuestServlet extends HttpServlet {
    private QuestService questService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        questService = (QuestService) servletContext.getAttribute(WebConstants.QUEST_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questId = (String) session.getAttribute(WebConstants.QUEST_ID.toString());
        if (questId != null) {
            QuestDTO questInfo = questService.getQuestDTO(questId);
            session.setAttribute(WebConstants.QUEST_INFO.toString(), questInfo);
        }
        getServletContext().getRequestDispatcher(WebConstants.QUEST_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(WebConstants.USER.toString());
        String questId = (String) session.getAttribute(WebConstants.QUEST_ID.toString());
        String questAnswerId = request.getParameter(WebConstants.QUEST_ANSWER_ID.toString());
        RoomDTO endedRoom = (RoomDTO) session.getAttribute(WebConstants.CURRENT_ROOM.toString());

        if (questService.questIsSuccess(questId, questAnswerId)) {
            questService.setUserLevelAndPoints(user, endedRoom);
            session.setAttribute(WebConstants.RESULT_QUEST.toString(), true);
        } else session.setAttribute(WebConstants.RESULT_QUEST.toString(), false);

        questService.closeRoom(user, endedRoom);
        getServletContext().getRequestDispatcher(WebConstants.QUEST_OVER_JSP.toString()).forward(request, response);
    }
}

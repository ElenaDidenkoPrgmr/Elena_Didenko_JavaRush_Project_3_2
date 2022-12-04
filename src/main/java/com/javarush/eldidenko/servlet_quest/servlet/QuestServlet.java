package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.User;

import com.javarush.eldidenko.servlet_quest.service.QuestService;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "questServlet", value = "/quest")
public class QuestServlet extends HttpServlet {
    private QuestService questService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        questService = (QuestService) servletContext.getAttribute(QUEST_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var questId = (String) session.getAttribute(QUEST_ID.toString());
        if (questId != null) {
            var questInfo = questService.getQuestDTO(questId);
            session.setAttribute(QUEST_INFO.toString(), questInfo);
        }
        getServletContext().getRequestDispatcher(QUEST_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var user = (User) session.getAttribute(USER.toString());
        var questId = (String) session.getAttribute(QUEST_ID.toString());
        var questAnswerId = request.getParameter(QUEST_ANSWER_ID.toString());
        var endedRoom = (RoomDTO) session.getAttribute(CURRENT_ROOM.toString());

        if (questService.questIsSuccess(questId, questAnswerId)) {
            questService.setUserLevelAndPoints(user, endedRoom);
            session.setAttribute(RESULT_QUEST.toString(), true);
        } else session.setAttribute(RESULT_QUEST.toString(), false);

        questService.closeRoom(user, endedRoom);
        getServletContext().getRequestDispatcher(QUEST_OVER_JSP.toString()).forward(request, response);
    }
}

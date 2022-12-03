package com.javarush.eldidenko.servlet;

import com.javarush.eldidenko.dto.QuestDTO;
import com.javarush.eldidenko.dto.RoomDTO;
import com.javarush.eldidenko.entity.User;

import com.javarush.eldidenko.service.QuestService;
import static com.javarush.eldidenko.servlet.WebConstants.*;

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
        questService = (QuestService) servletContext.getAttribute(QUEST_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questId = (String) session.getAttribute(QUEST_ID.toString());
        if (questId != null) {
            QuestDTO questInfo = questService.getQuestDTO(questId);
            session.setAttribute(QUEST_INFO.toString(), questInfo);
        }
        getServletContext().getRequestDispatcher(QUEST_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER.toString());
        String questId = (String) session.getAttribute(QUEST_ID.toString());
        String questAnswerId = request.getParameter(QUEST_ANSWER_ID.toString());
        RoomDTO endedRoom = (RoomDTO) session.getAttribute(CURRENT_ROOM.toString());

        if (questService.questIsSuccess(questId, questAnswerId)) {
            questService.setUserLevelAndPoints(user, endedRoom);
            session.setAttribute(RESULT_QUEST.toString(), true);
        } else session.setAttribute(RESULT_QUEST.toString(), false);

        questService.closeRoom(user, endedRoom);
        getServletContext().getRequestDispatcher(QUEST_OVER_JSP.toString()).forward(request, response);
    }
}

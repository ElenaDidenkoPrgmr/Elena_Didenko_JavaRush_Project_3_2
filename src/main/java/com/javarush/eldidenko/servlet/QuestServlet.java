package com.javarush.eldidenko.servlet;

import com.javarush.eldidenko.dto.QuestDTO;
import com.javarush.eldidenko.dto.RoomDTO;
import com.javarush.eldidenko.entity.User;

import com.javarush.eldidenko.service.QuestService;

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
        questService = (QuestService) servletContext.getAttribute("questsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questId = (String) session.getAttribute("questId");
        if (questId != null) {
            QuestDTO questInfo = questService.getQuestDTO(questId);
            session.setAttribute("questInfo", questInfo);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String questId = (String) session.getAttribute("questId");
        String questAnswerId = request.getParameter("questAnswerId");
        RoomDTO endedRoom = (RoomDTO) session.getAttribute("currentRoom");

        if (questService.questIsSuccess(questId, questAnswerId)) {
            questService.setUserLevelAndPoints(user, endedRoom);
            session.setAttribute("resultQuest", true);
        } else session.setAttribute("resultQuest", false);

        questService.closeRoom(user, endedRoom);
        getServletContext().getRequestDispatcher("/WEB-INF/questOver.jsp").forward(request, response);
    }
}

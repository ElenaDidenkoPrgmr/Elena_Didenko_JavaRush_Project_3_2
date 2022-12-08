package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Room;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "roomServlet", value = "/room")
public class RoomServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RoomServlet.class);
    private static final String USER_GAME_OVER = "User: {} ends game";
    private RoomService roomService;
    private User user = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        roomService = (RoomService) servletContext.getAttribute(ROOM_SERVICE.toString());
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        var httpServletRequest = (HttpServletRequest) request;
        user = (User) httpServletRequest.getSession().getAttribute(USER.toString());
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var session = request.getSession();
        if (user == null) {
            getServletContext().getRequestDispatcher(INDEX_JSP.toString()).forward(request, response);
        }

        var currentRoomId = user.getCurrentRoomId();
        if (currentRoomId == Room.FINISH_ROOM_ID) {
            getServletContext().getRequestDispatcher(GAME_OVER_JSP.toString()).forward(request, response);
            LOGGER.debug(USER_GAME_OVER, user.getName());
            return;
        }

        var currentRoom = roomService.getRoomByRoomId(currentRoomId);
        session.setAttribute(CURRENT_ROOM.toString(), currentRoom.getRoomDTO());

        List<NpcDTO> npcList = roomService.getNpcDTOList(currentRoom);
        session.setAttribute(NPC_REPOSITORY.toString(), npcList);
        getServletContext().getRequestDispatcher(ROOM_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var nextRoom = Integer.valueOf(request.getParameter(NEXT_ROOM.toString()));
        if (nextRoom != null) {
            user.setCurrentRoomId(nextRoom);
        }
        response.sendRedirect(ROOM_PAGE.toString());
    }
}

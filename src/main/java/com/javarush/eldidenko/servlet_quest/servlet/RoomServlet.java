package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.Npc;
import com.javarush.eldidenko.servlet_quest.entity.Room;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.NpcRepository;
import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.repository.RoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "roomServlet", value = "/room")
public class RoomServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RoomServlet.class);
    private static final String USER_GAME_OVER = "User: {} ends game";
    private Repository<Integer, Room> roomRepository = null;
    private Repository<Integer, Npc> npcRepository = null;
    private User user = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        roomRepository = (RoomRepository) servletContext.getAttribute(ROOMS_REPOSITORY.toString());
        npcRepository = (NpcRepository) servletContext.getAttribute(NPC_REPOSITORY.toString());
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

        var currentRoom = roomRepository.getById(currentRoomId);
        var roomInfo = RoomDTO.builder()
                .id(currentRoomId)
                .name(currentRoom.getName())
                .level(currentRoom.getLevel())
                .build();

        session.setAttribute(CURRENT_ROOM.toString(), roomInfo);

        List<NpcDTO> npcList = new ArrayList<>();
        for (var npcId : currentRoom.getNpc()) {
            npcList.add(NpcDTO.builder()
                    .id(npcId)
                    .name(npcRepository.getById(npcId).getName())
                    .avatar(npcRepository.getById(npcId).getAvatar())
                    .description(npcRepository.getById(npcId).getDescription())
                    .build());

        }
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

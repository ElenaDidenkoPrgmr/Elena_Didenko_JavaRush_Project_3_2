package com.javarush.eldidenko.servlet;

import com.javarush.eldidenko.dto.NpcDTO;
import com.javarush.eldidenko.dto.RoomDTO;
import com.javarush.eldidenko.entity.Npc;
import com.javarush.eldidenko.entity.Room;
import com.javarush.eldidenko.entity.User;
import com.javarush.eldidenko.repository.NpcRepository;
import com.javarush.eldidenko.repository.Repository;
import com.javarush.eldidenko.repository.RoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.javarush.eldidenko.servlet.WebConstants.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "roomServlet", value = "/room")
public class RoomServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RoomServlet.class);
    private Repository<Integer, Room> roomRepository = null;
    private Repository<Integer, Npc> npcRepository = null;
    private User user = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        roomRepository = (RoomRepository) servletContext.getAttribute(ROOMS_REPOSITORY.toString());
        npcRepository = (NpcRepository) servletContext.getAttribute(NPC_REPOSITORY.toString());
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        user = (User) httpServletRequest.getSession().getAttribute(USER.toString());
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (user == null){
            getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }

        int currentRoomId = user.getCurrentRoomId();
        if (currentRoomId == Room.FINISH_ROOM_ID) {
            getServletContext().getRequestDispatcher("/WEB-INF/gameOver.jsp").forward(request, response);
            LOGGER.debug("User: " + user.getName() + " ends game");
            return;
        }

        Room currentRoom = roomRepository.getById(currentRoomId);
        RoomDTO roomInfo = RoomDTO.builder()
                .id(currentRoomId)
                .name(currentRoom.getName())
                .level(currentRoom.getLevel())
                .build();

        session.setAttribute(CURRENT_ROOM.toString(), roomInfo);

        List<NpcDTO> npcList = new ArrayList<>();
        for (Integer npcId : currentRoom.getNpc()) {
            npcList.add(NpcDTO.builder()
                            .id(npcId)
                            .name(npcRepository.getById(npcId).getName())
                            .avatar(npcRepository.getById(npcId).getAvatar())
                            .description(npcRepository.getById(npcId).getDescription())
                    .build());

        }
        session.setAttribute(NPC_REPOSITORY.toString(), npcList);
        getServletContext().getRequestDispatcher("/WEB-INF/room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer nextRoom = Integer.valueOf(request.getParameter(NEXT_ROOM.toString()));
        if (nextRoom != null) {
            user.setCurrentRoomId(nextRoom);
        }
        response.sendRedirect("/room");
    }
}

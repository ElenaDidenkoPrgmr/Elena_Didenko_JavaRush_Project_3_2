package servlet;

import com.javarush.dto.RoomDTO;
import com.javarush.entity.Npc;
import com.javarush.entity.Room;
import com.javarush.entity.User;
import com.javarush.repository.NpcRepository;
import com.javarush.repository.RoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private RoomRepository roomRepository = null;
    private NpcRepository npcRepository = null;
    User user = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        roomRepository = (RoomRepository) servletContext.getAttribute("rooms");
        npcRepository = (NpcRepository) servletContext.getAttribute("npcs");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        user = (User) httpServletRequest.getSession().getAttribute("user");
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int currentRoomId = user.getCurrentRoomId();
        if (currentRoomId == Room.FINISH_ROOM_ID) {
            getServletContext().getRequestDispatcher("/WEB-INF/gameOver.jsp").forward(request, response);
            LOGGER.debug("User: " + user.getName() + "ends game");
            return;
        }

        Room currentRoom = roomRepository.get(currentRoomId);
        RoomDTO roomInfo = RoomDTO.builder()
                .id(currentRoomId)
                .name(currentRoom.getName())
                .level(currentRoom.getLevel())
                .build();

        session.setAttribute("currentRoom", roomInfo);

        List<Npc> npcList = new ArrayList<>();
        for (Integer npcId : currentRoom.getNpc()) {
            npcList.add(npcRepository.get(npcId));
        }
        session.setAttribute("npcs", npcList);
        getServletContext().getRequestDispatcher("/WEB-INF/room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer nextRoom = Integer.valueOf(request.getParameter("nextRoom"));
        if (nextRoom != null) {
            user.setCurrentRoomId(nextRoom);
        }
        response.sendRedirect("/room");
    }
}

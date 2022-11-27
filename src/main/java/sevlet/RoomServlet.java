package sevlet;

import com.javarush.dto.RoomDTO;
import com.javarush.entity.Npc;
import com.javarush.entity.Room;
import com.javarush.entity.User;
import com.javarush.repository.NpcRepository;
import com.javarush.repository.RoomRepository;
import com.javarush.repository.UserRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "roomServlet", value = "/room")
public class RoomServlet extends HttpServlet {
    private RoomRepository roomRepository = null;
    private UserRepository userRepository = null;
    private NpcRepository npcRepository = null;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        roomRepository = (RoomRepository) servletContext.getAttribute("rooms");
        userRepository = (UserRepository) servletContext.getAttribute("users");
        npcRepository = (NpcRepository) servletContext.getAttribute("npcs");

        System.out.println("Init method with ServletConfig ended");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) request.getSession().getAttribute("user");



        int currentRoomId = user.getCurrentRoomId();//--
        int userLevel = user.getLevel();
        if (currentRoomId == Room.FINISH_ROOM_ID) {
            getServletContext().getRequestDispatcher("/WEB-INF/gameOver.jsp").forward(request, response);
            return;
        }
        if (userLevel == Room.FINISH_LEVEL) {
            getServletContext().getRequestDispatcher("/WEB-INF/gameOver.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Integer nextRoom = Integer.valueOf(request.getParameter("nextRoom"));
        if (nextRoom != null) {
            user.setCurrentRoomId(nextRoom);
        }
        response.sendRedirect("/room");
    }


}

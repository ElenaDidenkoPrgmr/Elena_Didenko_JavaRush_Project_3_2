package servlet;

import com.javarush.dto.RoomDTO;
import com.javarush.entity.*;
import com.javarush.repository.QuestRepository;

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
    private QuestRepository questRepository = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        questRepository = (QuestRepository) servletContext.getAttribute("quests");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Quest quest;
        String questId = (String) session.getAttribute("questId");
        if (questId != null) {
            quest = questRepository.get(Integer.parseInt(questId));
            session.setAttribute("quest", quest);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String questAnswer = request.getParameter("questAnswer");
        if ("true".equals(questAnswer)) {
            setUserLevel(user, session);
            session.setAttribute("resultQuest", true);
        }else session.setAttribute("resultQuest", false);

        RoomDTO currentRoom = (RoomDTO) session.getAttribute("currentRoom");
        Integer currentRoomId = currentRoom.getId();
        user.getEndedQuest().add(currentRoomId);
        getServletContext().getRequestDispatcher("/WEB-INF/questOver.jsp").forward(request, response);
    }

    private void setUserLevel(User user, HttpSession session) {
        int currentUserLevel = user.getLevel();
        RoomDTO currentRoom = (RoomDTO) session.getAttribute("currentRoom");
        int currentRoomLevel = currentRoom.getLevel();
        int currentPoint = user.getPoint();
        if (currentUserLevel == currentRoomLevel){
            user.setLevel(currentRoomLevel + 1);
            user.setPoint(currentPoint + currentRoomLevel * 10);
        }
    }
}

package sevlet;

import com.javarush.entity.Room;
import com.javarush.entity.User;
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

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UserRepository userRepository = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userRepository = (UserRepository) servletContext.getAttribute("users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initUser(request);
        response.sendRedirect("/room");
    }

    private void initUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = request.getParameter("username");
        User user = new User();
        int totalGame = User.START_TOTAL_GAME;

        if (userName != null) {
            userName.trim();
        } else {
            userName = (String) request.getSession().getAttribute("username");
            totalGame = userRepository.get(userName).getTotalGame() + 1;
        }

        user.setName(userName);
        user.setTotalGame(totalGame);
        user.setCurrentRoomId(Room.START_ROOM_ID);
       // user.setLevel(Room.START_LEVEL);
        user.setLevel(3);//удалить
        user.setPoint(User.START_POINT);
        user.setEndedQuest(new ArrayList<>());
        userRepository.add(userName, user);

        synchronized (session) {
           // session.setAttribute("username", userName);
            session.setAttribute("user", user);
        }
    }
}

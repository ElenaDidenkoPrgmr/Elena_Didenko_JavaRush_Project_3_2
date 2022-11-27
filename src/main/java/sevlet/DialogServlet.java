package sevlet;

import com.javarush.dto.RoomDTO;
import com.javarush.entity.*;
import com.javarush.repository.DialogRepository;
import com.javarush.repository.NpcRepository;
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

@WebServlet(name = "dialogServlet", value = "/dialog")
public class DialogServlet extends HttpServlet {
    private NpcRepository npcRepository = null;
    private DialogRepository dialogRepository = null;
    private QuestRepository questRepository = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        npcRepository = (NpcRepository) servletContext.getAttribute("npcs");
        dialogRepository = (DialogRepository) servletContext.getAttribute("dialogs");
        questRepository = (QuestRepository) servletContext.getAttribute("quests");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/dialog.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //if (session.getAttribute())
        //int npcId = (int) session.getAttribute("npc");

        Npc npc;
        String npcId = request.getParameter("npc");
        if (npcId != null) {
            npc = npcRepository.get(Integer.parseInt(npcId));
            session.setAttribute("npc",npc);
        }else {
            npc = (Npc) session.getAttribute("npc");
        }


        String questId = request.getParameter("quest");
        if (questId != null) {
            session.setAttribute("questId",questId);

            response.sendRedirect("/quest");
            return;
        }

        QuestionNpc question;
        String nextQuestionId = request.getParameter("nextQuestion");

        if (nextQuestionId != null) {
            question = dialogRepository.get(Integer.parseInt(nextQuestionId));
        } else {
            int questionId = npc.getStartMessageId();
            question = dialogRepository.get(questionId);
        }


        session.setAttribute("question", question);
        response.sendRedirect("/dialog");

    }
}

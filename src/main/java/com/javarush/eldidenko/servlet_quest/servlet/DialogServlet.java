package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Npc;

import com.javarush.eldidenko.servlet_quest.repository.DialogRepository;
import com.javarush.eldidenko.servlet_quest.repository.NpcRepository;
import com.javarush.eldidenko.servlet_quest.repository.Repository;

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
    private Repository<Integer, Npc> npcRepository = null;
    private Repository<Integer, Dialog> dialogRepository = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        npcRepository = (NpcRepository) servletContext.getAttribute(WebConstants.NPC_REPOSITORY.toString());
        dialogRepository = (DialogRepository) servletContext.getAttribute(WebConstants.DIALOG_REPOSITORY.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(WebConstants.DIALOG_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        Npc npc;
        String npcId = request.getParameter(WebConstants.NPC_ID.toString());
        if (npcId != null) {
            npc = npcRepository.getById(Integer.parseInt(npcId));

            NpcDTO npcInfo = NpcDTO.builder()
                    .id(Integer.valueOf(npcId))
                    .name(npc.getName())
                    .avatar(npc.getAvatar())
                    .description(npc.getDescription())
                    .build();

            session.setAttribute(WebConstants.NPC_INFO.toString(), npcInfo);
        }else {
            NpcDTO npcDTO = (NpcDTO) session.getAttribute(WebConstants.NPC_INFO.toString());
            npc = npcRepository.getById(npcDTO.getId());
        }

        String questId = request.getParameter(WebConstants.QUEST_ID.toString());
        if (questId != null) {
            session.setAttribute(WebConstants.QUEST_ID.toString(), questId);
            response.sendRedirect(WebConstants.QUEST_PAGE.toString());
            return;
        }

        Dialog dialog;
        String nextQuestionId = request.getParameter(WebConstants.NEXT_QUESTION.toString());

        if (nextQuestionId != null) {
            dialog = dialogRepository.getById(Integer.parseInt(nextQuestionId));
        } else {
            int questionId = npc.getStartMessageId();
            dialog = dialogRepository.getById(questionId);
        }

        session.setAttribute(WebConstants.DIALOG.toString(), dialog);
        response.sendRedirect(WebConstants.DIALOG_PAGE.toString());
    }
}

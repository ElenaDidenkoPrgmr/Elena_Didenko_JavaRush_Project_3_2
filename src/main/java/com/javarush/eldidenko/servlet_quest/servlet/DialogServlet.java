package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Npc;

import com.javarush.eldidenko.servlet_quest.repository.DialogRepository;
import com.javarush.eldidenko.servlet_quest.repository.NpcRepository;
import com.javarush.eldidenko.servlet_quest.repository.Repository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

import java.io.IOException;

@WebServlet(name = "dialogServlet", value = "/dialog")
public class DialogServlet extends HttpServlet {
    private Repository<Integer, Npc> npcRepository = null;
    private Repository<Integer, Dialog> dialogRepository = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        npcRepository = (NpcRepository) servletContext.getAttribute(NPC_REPOSITORY.toString());
        dialogRepository = (DialogRepository) servletContext.getAttribute(DIALOG_REPOSITORY.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(DIALOG_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession();

        Npc npc;
        var npcId = request.getParameter(NPC_ID.toString());
        if (npcId != null) {
            npc = npcRepository.getById(Integer.parseInt(npcId));

            var npcInfo = NpcDTO.builder()
                    .id(Integer.valueOf(npcId))
                    .name(npc.getName())
                    .avatar(npc.getAvatar())
                    .description(npc.getDescription())
                    .build();

            session.setAttribute(NPC_INFO.toString(), npcInfo);
        } else {
            var npcDTO = (NpcDTO) session.getAttribute(NPC_INFO.toString());
            npc = npcRepository.getById(npcDTO.getId());
        }

        var questId = request.getParameter(QUEST_ID.toString());
        if (questId != null) {
            session.setAttribute(QUEST_ID.toString(), questId);
            response.sendRedirect(QUEST_PAGE.toString());
            return;
        }

        Dialog dialog;
        var nextQuestionId = request.getParameter(NEXT_QUESTION.toString());

        if (nextQuestionId != null) {
            dialog = dialogRepository.getById(Integer.parseInt(nextQuestionId));
        } else {
            int questionId = npc.getStartMessageId();
            dialog = dialogRepository.getById(questionId);
        }

        session.setAttribute(DIALOG.toString(), dialog);
        response.sendRedirect(DIALOG_PAGE.toString());
    }
}

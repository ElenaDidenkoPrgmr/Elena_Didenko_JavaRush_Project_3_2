package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Npc;

import com.javarush.eldidenko.servlet_quest.service.DialogService;

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
    private DialogService dialogService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        dialogService = (DialogService) servletContext.getAttribute(DIALOG_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(DIALOG_JSP.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession();

        var questId = request.getParameter(QUEST_ID.toString());
        if (questId != null) {
            session.setAttribute(QUEST_ID.toString(), questId);
            response.sendRedirect(QUEST_PAGE.toString());
            return;
        }

        var npcId = request.getParameter(NPC_ID.toString());
        var npcDTO = (NpcDTO) session.getAttribute(NPC_INFO.toString());

        Npc currentNpc = dialogService.getNpcByIdOrDTO(npcId, npcDTO);
        session.setAttribute(NPC_INFO.toString(), currentNpc.getNpcDTO());

        var nextQuestionId = request.getParameter(NEXT_QUESTION.toString());
        Dialog dialog = dialogService.getDialogByQuestionId(nextQuestionId, currentNpc);

        session.setAttribute(DIALOG.toString(), dialog);
        response.sendRedirect(DIALOG_PAGE.toString());
    }
}

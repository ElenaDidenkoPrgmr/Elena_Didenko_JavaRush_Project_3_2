package com.javarush.eldidenko.servlet_quest.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;
import java.io.IOException;

@WebServlet(name = "indexServlet", value = "")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher(INDEX_JSP.toString()).forward(request, response);
    }
}

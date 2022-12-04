package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.javarush.eldidenko.servlet_quest.service.LoginService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    private LoginService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        userService = (LoginService) servletContext.getAttribute(LOGIN_SERVICE.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession();
        var userName = request.getParameter(USERNAME.toString());
        if (userName == null) {
            var existingUser = (User) request.getSession().getAttribute(USER.toString());
            userName = existingUser.getName();
        }
        var user = userService.initUser(userName);
        session.setAttribute(USER.toString(), user);

        response.sendRedirect(ROOM_PAGE.toString());
    }
}

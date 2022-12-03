package com.javarush.eldidenko.servlet;

import com.javarush.eldidenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.javarush.eldidenko.service.LoginService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.javarush.eldidenko.servlet.WebConstants.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    private LoginService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (LoginService) servletContext.getAttribute(LOGIN_SERVICE.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userName = request.getParameter(USERNAME.toString());
        if (userName == null) {
            User existingUser = (User) request.getSession().getAttribute(USER.toString());
            userName = existingUser.getName();
        }
        User user = userService.initUser(userName);
        session.setAttribute(USER.toString(), user);

        response.sendRedirect("/room");
    }
}

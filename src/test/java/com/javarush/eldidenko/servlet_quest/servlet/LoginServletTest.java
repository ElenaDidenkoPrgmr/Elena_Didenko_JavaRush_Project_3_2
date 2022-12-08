package com.javarush.eldidenko.servlet_quest.servlet;

import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.service.LoginService;
import com.javarush.eldidenko.servlet_quest.servlet.LoginServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoginServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletConfig servletConfig;

    @Mock
    ServletContext context;

    @Mock
    LoginService userService;

    LoginServlet loginServlet;

    @BeforeEach
    void setup() throws ServletException {
        when(servletConfig.getServletContext())
                .thenReturn(context);

        when(context.getAttribute(eq(LOGIN_SERVICE.toString())))
                .thenReturn(userService);

        when(request.getSession())
                .thenReturn(session);

        loginServlet = new LoginServlet();
        loginServlet.init(servletConfig);
    }

    @Test
    void test_doPost_WhenUserIsNotNew() throws ServletException, IOException {
        User user = new User();
        user.setName("test");
        user.setTotalGame(5);

        when(request.getParameter(USERNAME.toString())).thenReturn(null);
        when(session.getAttribute(USER.toString())).thenReturn(user);
        when(userService.initUser("test")).thenReturn(user);
        loginServlet.doPost(request,response);

        verify(session, times(1)).setAttribute(USER.toString(), user);
        verify(response, times(1)).sendRedirect("/room");
    }

    @Test
    void test_doPost_WhenUserIsNew() throws ServletException, IOException {
        User user = new User();
        user.setName("test");
        user.setTotalGame(5);

        when(request.getParameter(USERNAME.toString())).thenReturn("test");
        when(userService.initUser("test")).thenReturn(user);
        loginServlet.doPost(request,response);

        verify(session, times(1)).setAttribute(USER.toString(), user);
        verify(response, times(1)).sendRedirect("/room");
    }
}

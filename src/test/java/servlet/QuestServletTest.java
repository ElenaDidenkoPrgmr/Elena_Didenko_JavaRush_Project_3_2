package servlet;

import com.javarush.service.LoginService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestServletTest {

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

        when(context.getAttribute(eq("loginService")))
                .thenReturn(userService);

        when(request.getSession())
                .thenReturn(session);

        loginServlet = new LoginServlet();
        loginServlet.init(servletConfig);
    }

    @Test
    void doGet() {
    }

    @Test
    void doPost() {
    }
}
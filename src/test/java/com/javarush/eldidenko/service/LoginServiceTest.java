package com.javarush.eldidenko.service;

import com.javarush.eldidenko.entity.User;
import com.javarush.eldidenko.repository.Repository;
import com.javarush.eldidenko.service.LoginService;
import com.javarush.eldidenko.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private Repository<String, User> userRepository;

    private LoginService loginService;

    @BeforeEach
    void init(){
        loginService = new LoginService(userRepository);
    }

    @Test
    void test_initUser_ShouldThrowException_WhenUsernameIsNull() {
        assertThrows(ServiceException.class,
                () -> loginService.initUser(null));
    }

    @Test
    void test_initUser_WhenUserIsNew() {
        User testUser = new User();
        testUser.setName("test");
        testUser.setCurrentRoomId(1);
        testUser.setEndedQuest(new ArrayList<>());

        loginService.initUser("test");
        verify(userRepository,times(1)).add("test",testUser);
    }

    @Test
    void test_initUser_WhenUserIsNotNew() {
        User testUser = new User();
        testUser.setName("test");
        testUser.setTotalGame(2);

        when(userRepository.containsId(eq("test"))).thenReturn(true);
        when(userRepository.getById(eq("test"))).thenReturn(testUser);

        testUser = loginService.initUser(testUser.getName());
        assertEquals(3,testUser.getTotalGame());
    }
}

package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.entity.Room;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.UserRepository;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class LoginService extends Service<String, User> {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User initUser(String userName) {
        if (userName == null) {
            var errorMsg = "userName can't be null";
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        var totalGame = User.START_TOTAL_GAME;
        if (userRepository.containsId(userName)) {
            totalGame = userRepository.getById(userName).getTotalGame() + 1;
        }
        var user = User.builder()
                .name(userName)
                .totalGame(totalGame)
                .currentRoomId(Room.START_ROOM_ID)
                .level(Room.START_LEVEL)
                .point(User.START_POINT)
                .endedQuest(new ArrayList<>())
                .build();

        userRepository.add(userName, user);
        LOGGER.debug("User: {} starts game", userName);
        return user;
    }
}

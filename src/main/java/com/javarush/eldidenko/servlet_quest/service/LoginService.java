package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.entity.Room;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class LoginService extends Service<String, User> {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    public LoginService(Repository<String, User> repository) {
        super(repository);
    }

    public User initUser(String userName) {
        if (userName == null) {
            var errorMsg = "userName can't be null";
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        var totalGame = User.START_TOTAL_GAME;
        if (repository.containsId(userName)) {
            totalGame = repository.getById(userName).getTotalGame() + 1;
        }
        var user = User.builder()
                .name(userName)
                .totalGame(totalGame)
                .currentRoomId(Room.START_ROOM_ID)
                .level(Room.START_LEVEL)
                .point(User.START_POINT)
                .endedQuest(new ArrayList<>())
                .build();

        repository.add(userName, user);
        LOGGER.debug("User: {} starts game", userName);
        return user;
    }
}

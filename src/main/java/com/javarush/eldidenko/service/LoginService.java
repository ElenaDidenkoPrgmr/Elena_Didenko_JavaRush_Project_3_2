package com.javarush.eldidenko.service;

import com.javarush.eldidenko.entity.Room;
import com.javarush.eldidenko.entity.User;
import com.javarush.eldidenko.repository.Repository;
import com.javarush.eldidenko.service.exception.ServiceException;
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
            String errorMsg = "userName can't be null";
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        User user = new User();
        int totalGame = User.START_TOTAL_GAME;
        if (repository.containsId(userName)) {
            totalGame = repository.getById(userName).getTotalGame() + 1;
        }

        user.setName(userName);
        user.setTotalGame(totalGame);
        user.setCurrentRoomId(Room.START_ROOM_ID);
        user.setLevel(Room.START_LEVEL);
        user.setPoint(User.START_POINT);
        user.setEndedQuest(new ArrayList<>());

        repository.add(userName, user);
        LOGGER.debug("User: " + userName + " starts game");
        return user;
    }
}

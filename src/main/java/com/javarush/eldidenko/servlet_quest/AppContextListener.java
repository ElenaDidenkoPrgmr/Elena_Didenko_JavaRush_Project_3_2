package com.javarush.eldidenko.servlet_quest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.eldidenko.servlet_quest.entity.Npc;
import com.javarush.eldidenko.servlet_quest.entity.Quest;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Room;

import static com.javarush.eldidenko.servlet_quest.servlet.WebConstants.*;

import com.javarush.eldidenko.servlet_quest.repository.*;
import com.javarush.eldidenko.servlet_quest.service.DialogService;
import com.javarush.eldidenko.servlet_quest.service.RoomService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.javarush.eldidenko.servlet_quest.service.LoginService;
import com.javarush.eldidenko.servlet_quest.service.QuestService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class);
    private static final String MESSAGE_ROOM_REPOSITORY_INITIALIZATION_SUCCESS =
            "RoomRepository initialization success";
    private static final String MESSAGE_NPC_REPOSITORY_INITIALIZATION_SUCCESS =
            "NpcRepository initialization success";
    private static final String MESSAGE_QUEST_SERVICE_INITIALIZATION_SUCCESS =
            "QuestService initialization success";
    private static final String MESSAGE_DIALOG_SERVICE_INITIALIZATION_SUCCESS =
            "DialogService initialization success";
    private static final String MESSAGE_ROOM_SERVICE_INITIALIZATION_SUCCESS =
            "RoomService initialization success";
    private static final String MESSAGE_LOGIN_SERVICE_INITIALIZATION_SUCCESS =
            "LoginService initialization success";
    private static final String MESSAGE_ERROR_INITIALIZATION_ROOMS_REPOSITORY =
            "Error during initialization RoomsRepository: ";
    private static final String MESSAGE_ERROR_INITIALIZATION_NPC_REPOSITORY =
            "Error during initialization NpcRepository: ";
    private static final String MESSAGE_ERROR_INITIALIZATION_DIALOG_REPOSITORY =
            "Error during initialization DialogRepository: ";
    private static final String MESSAGE_ERROR_INITIALIZATION_QUEST_REPOSITORY =
            "Error during initialization QuestRepository: ";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        var servletContext = servletContextEvent.getServletContext();

        initializationQuestsService(servletContext);
        initializationUsersService(servletContext);
        initializationDialogsService(servletContext);
        initializationRoomService(servletContext);

        initializationNpcRepository(servletContext);
        initializationRoomRepository(servletContext);
    }

    private void initializationDialogsService(ServletContext servletContext) {
        DialogRepository dialogRepository = createDialogsRepository();
        NpcRepository npcRepository = createNpcRepository();
        servletContext.setAttribute(DIALOG_SERVICE.toString(),
                new DialogService(dialogRepository, npcRepository));
        LOGGER.debug(MESSAGE_DIALOG_SERVICE_INITIALIZATION_SUCCESS);
    }

    private void initializationRoomService(ServletContext servletContext) {
        RoomRepository roomRepository = createRoomRepository();
        NpcRepository npcRepository = createNpcRepository();
        servletContext.setAttribute(ROOM_SERVICE.toString(),
                new RoomService(roomRepository, npcRepository));
        LOGGER.debug(MESSAGE_ROOM_SERVICE_INITIALIZATION_SUCCESS);
    }

    private void initializationUsersService(ServletContext servletContext) {
        var userRepository = new UserRepository(new HashMap<>());
        servletContext.setAttribute(LOGIN_SERVICE.toString(), new LoginService(userRepository));
        LOGGER.debug(MESSAGE_LOGIN_SERVICE_INITIALIZATION_SUCCESS);
    }

    private void initializationQuestsService(ServletContext servletContext) {
        QuestRepository questRepository = createQuestRepository();
        servletContext.setAttribute(QUEST_SERVICE.toString(), new QuestService(questRepository));
        LOGGER.debug(MESSAGE_QUEST_SERVICE_INITIALIZATION_SUCCESS);
    }

    private void initializationNpcRepository(ServletContext servletContext) {
        var npcRepository = createNpcRepository();
        servletContext.setAttribute(NPC_REPOSITORY.toString(), npcRepository);
        LOGGER.debug(MESSAGE_NPC_REPOSITORY_INITIALIZATION_SUCCESS);
    }

    private void initializationRoomRepository(ServletContext servletContext) {
        var roomRepository = createRoomRepository();
        servletContext.setAttribute(ROOM_REPOSITORY.toString(), roomRepository);
        LOGGER.debug(MESSAGE_ROOM_REPOSITORY_INITIALIZATION_SUCCESS);
    }

    private NpcRepository createNpcRepository() {
        var mapper = new JsonMapper();
        var contents = getFileContents("/npcs.json");
        Npc[] npcsArray;

        try {
            npcsArray = mapper.readValue(contents, Npc[].class);
        } catch (IOException e) {
            String error = MESSAGE_ERROR_INITIALIZATION_NPC_REPOSITORY + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        var initialMapNpc = Arrays.stream(npcsArray)
                .collect(Collectors.toMap(Npc::getId, Function.identity()));
        return new NpcRepository(Map.copyOf(initialMapNpc));
    }

    private QuestRepository createQuestRepository() {
        var mapper = new JsonMapper();
        var contents = getFileContents("/quests.json");
        Quest[] questArray;
        try {
            questArray = mapper.readValue(contents, Quest[].class);
        } catch (IOException e) {
            var error = MESSAGE_ERROR_INITIALIZATION_QUEST_REPOSITORY + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        var initialMapQuest = Arrays.stream(questArray)
                .collect(Collectors.toMap(Quest::getId, Function.identity()));

        return new QuestRepository(Map.copyOf(initialMapQuest));
    }

    private DialogRepository createDialogsRepository() {
        var mapper = new JsonMapper();
        var contents = getFileContents("/dialogs.json");
        Dialog[] dialogsArray;

        try {
            dialogsArray = mapper.readValue(contents, Dialog[].class);
        } catch (IOException e) {
            var error = MESSAGE_ERROR_INITIALIZATION_DIALOG_REPOSITORY + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        var initialMapDialog = Arrays.stream(dialogsArray)
                .collect(Collectors.toMap(Dialog::getId, Function.identity()));
        return new DialogRepository(Map.copyOf(initialMapDialog));
    }

    private RoomRepository createRoomRepository() {
        var mapper = new JsonMapper();
        var contents = getFileContents("/rooms.json");
        Room[] roomsArray;

        try {
            roomsArray = mapper.readValue(contents, Room[].class);
        } catch (JsonProcessingException e) {
            String error = MESSAGE_ERROR_INITIALIZATION_ROOMS_REPOSITORY + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        var initialMapRoom = Arrays.stream(roomsArray)
                .collect(Collectors.toMap(Room::getId, Function.identity()));
        return new RoomRepository(Map.copyOf(initialMapRoom));
    }

    private String getFileContents(String fileName) {
        String contents;
        var inputStream = getClass().getResourceAsStream(fileName);
        var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        contents = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
        return contents;
    }
}

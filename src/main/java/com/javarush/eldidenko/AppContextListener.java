package com.javarush.eldidenko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.eldidenko.entity.Npc;
import com.javarush.eldidenko.entity.Quest;
import com.javarush.eldidenko.entity.Dialog;
import com.javarush.eldidenko.entity.Room;
import com.javarush.eldidenko.repository.*;
import static com.javarush.eldidenko.servlet.WebConstants.*;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.javarush.eldidenko.service.LoginService;
import com.javarush.eldidenko.service.QuestService;

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

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        initializationRooms(servletContext);
        initializationNpcs(servletContext);
        initializationDialogs(servletContext);
        initializationQuests(servletContext);
        initializationUsers(servletContext);
    }

    private String getFileContents(String fileName) {
        String contents;
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        contents = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
        return contents;
    }

    private void initializationRooms(ServletContext servletContext) {
        ObjectMapper mapper = new JsonMapper();
        String contents = getFileContents("/rooms.json");
        Room[] roomsArray;

        try {
            roomsArray = mapper.readValue(contents, Room[].class);
        } catch (JsonProcessingException e) {
            String error = "Error during initialization RoomsRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        Map<Integer, Room> initialMapRoom = Arrays.stream(roomsArray)
                .collect(Collectors.toMap(Room::getId, Function.identity()));

        RoomRepository roomRepository = new RoomRepository(Map.copyOf(initialMapRoom));
        servletContext.setAttribute(ROOMS_REPOSITORY.toString(), roomRepository);
        LOGGER.debug("RoomRepository initialization success");
    }

    private void initializationNpcs(ServletContext servletContext) {
        ObjectMapper mapper = new JsonMapper();
        String contents = getFileContents("/npcs.json");
        Npc[] npcsArray;

        try {
            npcsArray = mapper.readValue(contents, Npc[].class);
        } catch (IOException e) {
            String error = "Error during initialization NpcRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        Map<Integer, Npc> initialMapNpc = Arrays.stream(npcsArray)
                .collect(Collectors.toMap(Npc::getId, Function.identity()));

        NpcRepository npcRepository = new NpcRepository(Map.copyOf(initialMapNpc));
        servletContext.setAttribute(NPC_REPOSITORY.toString(), npcRepository);
        LOGGER.debug("NpcRepository initialization success");
    }

    private void initializationDialogs(ServletContext servletContext) {
        ObjectMapper mapper = new JsonMapper();
        String contents = getFileContents("/dialogs.json");
        Dialog[] dialogsArray;

        try {
            dialogsArray = mapper.readValue(contents, Dialog[].class);
        } catch (IOException e) {
            String error = "Error during initialization DialogRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        Map<Integer, Dialog> initialMapDialog = Arrays.stream(dialogsArray)
                .collect(Collectors.toMap(Dialog::getId, Function.identity()));

        DialogRepository dialogRepository = new DialogRepository(Map.copyOf(initialMapDialog));
        servletContext.setAttribute(DIALOG_REPOSITORY.toString(), dialogRepository);
        LOGGER.debug("DialogRepository initialization success");
    }

    private void initializationQuests(ServletContext servletContext) {
        ObjectMapper mapper = new JsonMapper();
        String contents = getFileContents("/quests.json");
        Quest[] questArray;
        try {questArray = mapper.readValue(contents, Quest[].class);
        } catch (IOException e) {
            String error = "Error during initialization QuestRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        Map<Integer, Quest> initialMapQuest = Arrays.stream(questArray)
                .collect(Collectors.toMap(Quest::getId, Function.identity()));

        QuestRepository questRepository = new QuestRepository(Map.copyOf(initialMapQuest));
        servletContext.setAttribute(QUEST_SERVICE.toString(), new QuestService(questRepository));
        LOGGER.debug("QuestRepository initialization success");
    }

    private static void initializationUsers(ServletContext servletContext) {
        UserRepository userRepository = new UserRepository(new HashMap<>());
        servletContext.setAttribute(LOGIN_SERVICE.toString(), new LoginService(userRepository));
        LOGGER.debug("UserRepository created");
    }
}

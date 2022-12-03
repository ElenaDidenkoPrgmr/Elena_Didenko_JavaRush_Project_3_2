package com.javarush.eldidenko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.eldidenko.entity.Npc;
import com.javarush.eldidenko.entity.Quest;
import com.javarush.eldidenko.entity.Dialog;
import com.javarush.eldidenko.entity.Room;
import com.javarush.eldidenko.repository.*;


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
import java.util.Collections;
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

        InitializationRooms(servletContext);
        InitializationNpcs(servletContext);
        InitializationDialogs(servletContext);
        InitializationQuests(servletContext);
        InitializationUsers(servletContext);
    }

    private void InitializationRooms(ServletContext servletContext) {
        ObjectMapper mapper = new JsonMapper();
        Map<Integer, Room> initialMapRoom;
        try (InputStream inputStream = getClass().getResourceAsStream("/rooms.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Room[] roomsArray = mapper.readValue(contents, Room[].class);
            initialMapRoom = Arrays.stream(roomsArray)
                    .collect(Collectors.toMap(Room::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization RoomRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        RoomRepository roomRepository = new RoomRepository(Collections.unmodifiableMap(initialMapRoom));
        servletContext.setAttribute("rooms", roomRepository);
        LOGGER.debug("RoomRepository initialization success");
    }

    private void InitializationNpcs(ServletContext servletContext) {
        ObjectMapper mapper2 = new JsonMapper();
        Map<Integer, Npc> initialMapNpc;
        try (InputStream inputStream = getClass().getResourceAsStream("/npc.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Npc[] npcArray = mapper2.readValue(contents, Npc[].class);
            initialMapNpc = Arrays.stream(npcArray)
                    .collect(Collectors.toMap(Npc::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization NpcRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        NpcRepository npcRepository = new NpcRepository(Collections.unmodifiableMap(initialMapNpc));
        servletContext.setAttribute("npcs", npcRepository);
        LOGGER.debug("NpcRepository initialization success");
    }

    private void InitializationDialogs(ServletContext servletContext) {
        ObjectMapper mapper3 = new JsonMapper();
        Map<Integer, Dialog> initialMapDialog;
        try (InputStream inputStream = getClass().getResourceAsStream("/dialog.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Dialog[] questionArray = mapper3.readValue(contents, Dialog[].class);
            initialMapDialog = Arrays.stream(questionArray)
                    .collect(Collectors.toMap(Dialog::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization DialogRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        DialogRepository dialogRepository = new DialogRepository(Collections.unmodifiableMap(initialMapDialog));
        servletContext.setAttribute("dialogs", dialogRepository);
        LOGGER.debug("DialogRepository initialization success");
    }

    private void InitializationQuests(ServletContext servletContext) {
        ObjectMapper mapper4 = new JsonMapper();
        Map<Integer, Quest> initialMapQuest;
        try (InputStream inputStream = getClass().getResourceAsStream("/quest.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Quest[] questArray = mapper4.readValue(contents, Quest[].class);
            initialMapQuest = Arrays.stream(questArray)
                    .collect(Collectors.toMap(Quest::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization QuestRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        QuestRepository questRepository = new QuestRepository(Collections.unmodifiableMap(initialMapQuest));
        servletContext.setAttribute("questsService", new QuestService(questRepository));
        LOGGER.debug("QuestRepository initialization success");
    }

    private static void InitializationUsers(ServletContext servletContext) {
        UserRepository userRepository = new UserRepository(new HashMap<>());
        servletContext.setAttribute("loginService", new LoginService(userRepository));
        LOGGER.debug("UserRepository created");
    }
}

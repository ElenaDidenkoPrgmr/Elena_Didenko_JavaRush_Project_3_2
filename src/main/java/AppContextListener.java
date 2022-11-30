import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.entity.Npc;
import com.javarush.entity.Quest;
import com.javarush.entity.Dialog;
import com.javarush.entity.Room;
import com.javarush.repository.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.javarush.service.LoginService;
import com.javarush.service.QuestService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ObjectMapper mapper = new JsonMapper();

        RoomRepository roomRepository = new RoomRepository(new HashMap<>());
        try (InputStream inputStream = getClass().getResourceAsStream("/rooms.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Room[] roomsArray = mapper.readValue(contents, Room[].class);
            roomRepository.repository = Arrays.stream(roomsArray)
                    .collect(Collectors.toMap(Room::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization RoomRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        servletContext.setAttribute("rooms", roomRepository);
        LOGGER.debug("RoomRepository initialization success");

        NpcRepository npcRepository = new NpcRepository(new HashMap<>());
        try (InputStream inputStream = getClass().getResourceAsStream("/npc.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Npc[] npcArray = mapper.readValue(contents, Npc[].class);
            npcRepository.repository = Arrays.stream(npcArray)
                    .collect(Collectors.toMap(Npc::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization NpcRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        servletContext.setAttribute("npcs", npcRepository);
        LOGGER.debug("NpcRepository initialization success");

        DialogRepository dialogRepository = new DialogRepository(new HashMap<>());
        try (InputStream inputStream = getClass().getResourceAsStream("/dialog.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Dialog[] questionArray = mapper.readValue(contents, Dialog[].class);
            dialogRepository.repository = Arrays.stream(questionArray)
                    .collect(Collectors.toMap(Dialog::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization DialogRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        servletContext.setAttribute("dialogs", dialogRepository);
        LOGGER.debug("DialogRepository initialization success");

        QuestRepository questRepository = new QuestRepository(new HashMap<>());
        try (InputStream inputStream = getClass().getResourceAsStream("/quest.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Quest[] questArray = mapper.readValue(contents, Quest[].class);
            questRepository.repository = Arrays.stream(questArray)
                    .collect(Collectors.toMap(Quest::getId, Function.identity()));
        } catch (IOException e) {
            String error = "Error during initialization QuestRepository" + e;
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        servletContext.setAttribute("questsService", new QuestService(questRepository));
        LOGGER.debug("QuestRepository initialization success");

        UserRepository userRepository = new UserRepository(new HashMap<>());
        servletContext.setAttribute("loginService", new LoginService(userRepository));
        LOGGER.debug("UserRepository created");
    }
}

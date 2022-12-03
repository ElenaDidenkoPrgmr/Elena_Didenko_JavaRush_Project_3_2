package com.javarush.eldidenko.service;

import com.javarush.eldidenko.dto.AnswerToQuestDTO;
import com.javarush.eldidenko.dto.QuestDTO;
import com.javarush.eldidenko.dto.RoomDTO;
import com.javarush.eldidenko.entity.Quest;
import com.javarush.eldidenko.entity.User;
import com.javarush.eldidenko.repository.Repository;
import com.javarush.eldidenko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class QuestService extends Service<Integer, Quest> {
    private static final Logger LOGGER = LogManager.getLogger(QuestService.class);
    private static final int COEFFICIENT_POINT = 10;

    public QuestService(Repository<Integer, Quest> repository) {
        super(repository);
    }

    private void checkParameterNotNull(Object parameter, String parameterName) {
        if (parameter == null) {
            String errorMsg = parameterName + " can't be null";
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    public QuestDTO getQuestDTO(String questIdValue) {
        checkParameterNotNull(questIdValue, "questId");
        Integer questId = parsingStringToInt(questIdValue, "questIdValue", LOGGER);

        Quest quest = repository.getById(questId);
        List<AnswerToQuestDTO> answerInfo = quest.getAnswerDTO();
        return QuestDTO.builder()
                .text(quest.getText())
                .answers(answerInfo)
                .build();
    }

    public void setUserLevelAndPoints(User user, RoomDTO endedRoomDTO) {
        checkParameterNotNull(user, "user");
        checkParameterNotNull(endedRoomDTO, "endedRoomDTO");

        int currentUserLevel = user.getLevel();
        int currentPoint = user.getPoint();

        int currentRoomLevel = endedRoomDTO.getLevel();

        if (currentUserLevel == currentRoomLevel) {
            user.setLevel(currentRoomLevel + 1);
            user.setPoint(currentPoint + currentRoomLevel * COEFFICIENT_POINT);
        }
    }

    public boolean questIsSuccess(String questIdValue, String answerValue) {
        checkParameterNotNull(questIdValue, "questId");
        checkParameterNotNull(answerValue, "answer");
        Integer questId = parsingStringToInt(questIdValue, "questIdValue", LOGGER);
        int answerId = parsingStringToInt(answerValue, "answer", LOGGER);

        Quest quest = repository.getById(questId);
        return quest.isRightAnswer(answerId);
    }

    public void closeRoom(User user, RoomDTO endedRoom) {
        checkParameterNotNull(user, "user");
        checkParameterNotNull(endedRoom, "endedRoom");

        Integer endedRoomId = endedRoom.getId();
        user.getEndedQuest().add(endedRoomId);
    }
}

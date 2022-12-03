package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.dto.QuestDTO;
import com.javarush.eldidenko.servlet_quest.dto.RoomDTO;
import com.javarush.eldidenko.servlet_quest.entity.Quest;
import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;

import com.javarush.eldidenko.servlet_quest.servlet.WebConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuestService extends Service<Integer, Quest> {
    private static final Logger LOGGER = LogManager.getLogger(QuestService.class);
    private static final int COEFFICIENT_POINT = 10;
    private static final String MESSAGE_NULL_ERROR = " can't be null";
    private static final String ADD_INFO_QUEST_ID_TO_LOGGER = "questIdValue";
    private static final String ADD_INFO_ANSWER_TO_LOGGER = "answer";
    private static final String ADD_INFO_ROOM_DTO_TO_LOGGER = "endedRoomDTO";
    private static final String ADD_INFO_ROOM_TO_LOGGER = "endedRoom";

    public QuestService(Repository<Integer, Quest> repository) {
        super(repository);
    }

    private void checkParameterNotNull(Object parameter, String parameterName) {
        if (parameter == null) {
            var errorMsg = parameterName + MESSAGE_NULL_ERROR;
            LOGGER.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    public QuestDTO getQuestDTO(String questIdValue) {
        checkParameterNotNull(questIdValue, WebConstants.QUEST_ID.toString());
        var questId = parsingStringToInt(questIdValue, ADD_INFO_QUEST_ID_TO_LOGGER, LOGGER);

        var quest = repository.getById(questId);
        var answerInfo = quest.getAnswerDTO();
        return QuestDTO.builder()
                .text(quest.getText())
                .answers(answerInfo)
                .build();
    }

    public void setUserLevelAndPoints(User user, RoomDTO endedRoomDTO) {
        checkParameterNotNull(user, WebConstants.USER.toString());
        checkParameterNotNull(endedRoomDTO, ADD_INFO_ROOM_DTO_TO_LOGGER);

        var currentUserLevel = user.getLevel();
        var currentPoint = user.getPoint();

        var currentRoomLevel = endedRoomDTO.getLevel();

        if (currentUserLevel == currentRoomLevel) {
            user.setLevel(currentRoomLevel + 1);
            user.setPoint(currentPoint + currentRoomLevel * COEFFICIENT_POINT);
        }
    }

    public boolean questIsSuccess(String questIdValue, String answerValue) {
        checkParameterNotNull(questIdValue, WebConstants.QUEST_ID.toString());
        checkParameterNotNull(answerValue, ADD_INFO_ANSWER_TO_LOGGER);
        var questId = parsingStringToInt(questIdValue, ADD_INFO_QUEST_ID_TO_LOGGER, LOGGER);
        var answerId = parsingStringToInt(answerValue, ADD_INFO_ANSWER_TO_LOGGER, LOGGER);

        var quest = repository.getById(questId);
        return quest.isRightAnswer(answerId);
    }

    public void closeRoom(User user, RoomDTO endedRoom) {
        checkParameterNotNull(user, WebConstants.USER.toString());
        checkParameterNotNull(endedRoom, ADD_INFO_ROOM_TO_LOGGER);

        var endedRoomId = endedRoom.getId();
        user.getEndedQuest().add(endedRoomId);
    }
}

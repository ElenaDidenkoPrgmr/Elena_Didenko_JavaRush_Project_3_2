package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Room;
import com.javarush.eldidenko.servlet_quest.repository.NpcRepository;
import com.javarush.eldidenko.servlet_quest.repository.RoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends Service<Integer, Dialog> {
    private static final Logger LOGGER = LogManager.getLogger(RoomService.class);

    private final RoomRepository roomRepository;
    private final NpcRepository npcRepository;

    public RoomService(RoomRepository roomRepository,
                       NpcRepository npcRepository) {
        this.roomRepository = roomRepository;
        this.npcRepository = npcRepository;
    }

    public List<NpcDTO> getNpcDTOList(Room currentRoom) {
        List<NpcDTO> npcList = new ArrayList<>();
        for (var npcId : currentRoom.getNpc()) {
            npcList.add(NpcDTO.builder()
                    .id(npcId)
                    .name(npcRepository.getById(npcId).getName())
                    .avatar(npcRepository.getById(npcId).getAvatar())
                    .description(npcRepository.getById(npcId).getDescription())
                    .build());

        }
        return npcList;
    }

    public Room getRoomByRoomId(int roomId) {
        return roomRepository.getById(roomId);
    }
}

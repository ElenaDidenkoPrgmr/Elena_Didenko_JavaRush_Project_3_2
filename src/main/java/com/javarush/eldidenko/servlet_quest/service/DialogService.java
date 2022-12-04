package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.dto.NpcDTO;
import com.javarush.eldidenko.servlet_quest.entity.Dialog;
import com.javarush.eldidenko.servlet_quest.entity.Npc;
import com.javarush.eldidenko.servlet_quest.repository.DialogRepository;
import com.javarush.eldidenko.servlet_quest.repository.NpcRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DialogService extends Service<Integer, Dialog> {
    private static final Logger LOGGER = LogManager.getLogger(DialogService.class);

    private final DialogRepository dialogRepository;
    private final NpcRepository npcRepository;


    public DialogService(DialogRepository dialogRepository,
                         NpcRepository npcRepository) {
        this.dialogRepository = dialogRepository;
        this.npcRepository = npcRepository;
    }

    public Dialog getDialogByQuestionId(String questionIdValue, Npc npc) {
        if (questionIdValue != null) {
            return dialogRepository.getById(Integer.parseInt(questionIdValue));
        } else {
            int questionId = npc.getStartMessageId();
            return dialogRepository.getById(questionId);
        }
    }
    public Npc getNpcByIdOrDTO(String npcId, NpcDTO npcDTO) {
        return (npcId != null) ? getNpcByNpcId(npcId) : getNpcByNpcDTO(npcDTO);
    }

    private Npc getNpcByNpcDTO(NpcDTO npcDTO) {
        return npcRepository.getById(npcDTO.getId());
    }

    private Npc getNpcByNpcId(String npcId) {
        return npcRepository.getById(parsingStringToInt(npcId, "npcId", LOGGER));
    }


}

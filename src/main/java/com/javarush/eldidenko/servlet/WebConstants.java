package com.javarush.eldidenko.servlet;

public enum WebConstants {
    USERNAME("username"),
    USER("user"),
    QUEST_ID ("questId"),
    NEXT_QUESTION ("nextQuestion"),
    QUEST_ANSWER_ID ("questAnswerId"),
    NPC_INFO ("npcInfo"),
    NPC_ID ("npcId"),
    DIALOG ("dialog"),
    ROOMS_REPOSITORY("rooms"),
    CURRENT_ROOM ("currentRoom"),
    NEXT_ROOM ("nextRoom"),
    RESULT_QUEST ("resultQuest"),
    LOGIN_SERVICE ("loginService"),
    QUEST_INFO ("questInfo"),
    QUEST_SERVICE ("questsService"),
    DIALOG_REPOSITORY ("dialogs"),
    NPC_REPOSITORY ("npcs");
    //DIALOG_PAGE ("/dialog");

    private final String text;

    WebConstants(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

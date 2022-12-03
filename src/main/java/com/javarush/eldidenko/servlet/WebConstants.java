package com.javarush.eldidenko.servlet;

public enum WebConstants {
    LOGIN_SERVICE ("loginService"),
    QUEST_SERVICE ("questsService"),
    ROOMS_REPOSITORY("rooms"),
    DIALOG_REPOSITORY ("dialogs"),
    NPC_REPOSITORY ("npcs"),
    USERNAME("username"),
    USER("user"),
    QUEST_ID ("questId"),
    QUEST_INFO ("questInfo"),
    QUEST_ANSWER_ID ("questAnswerId"),
    RESULT_QUEST ("resultQuest"),
    NPC_INFO ("npcInfo"),
    NPC_ID ("npcId"),
    DIALOG ("dialog"),
    NEXT_QUESTION ("nextQuestion"),
    CURRENT_ROOM ("currentRoom"),
    NEXT_ROOM ("nextRoom");
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

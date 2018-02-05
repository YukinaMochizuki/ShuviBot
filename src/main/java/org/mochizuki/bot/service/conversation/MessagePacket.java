package org.mochizuki.bot.service.conversation;

import java.util.ArrayList;

public class MessagePacket {
    private String originalMessage;
    private String message;
    private boolean isCommand = false;
    private ArrayList<String> parameter = new ArrayList<>();

    public ArrayList<String> getParameter() {
        return parameter;
    }

    public boolean isCommand() {
        return isCommand;
    }

    public String getMessage() {
        return message;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setCommand(boolean command) {
        isCommand = command;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public void setParameter(ArrayList<String> parameter) {
        this.parameter = parameter;
    }
}

package org.mochizuki.bot.service;

import org.mochizuki.bot.service.conversation.*;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

public class ConversationManager {
    private boolean allReady = false;
    private Logger logger;
    private BasicIO basicIO;
    private ServiceManager serviceManager;

    private ConversationMode conversationMode = ConversationMode.TalkMode;
    private ModeManager modeManager;
    private SingleMode singleMode;
    private TalkMode talkMode;


    ConversationManager(ServiceManager serviceManager){
        this.logger = Logger.getLogger("Conversation Manager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());
        this.serviceManager = serviceManager;
        this.basicIO = serviceManager.getBasicIO();

        this.modeManager = new ModeManager();
        this.singleMode = new SingleMode(this);
        this.talkMode = new TalkMode();
    }

    public void communicate(String input){
        switch (conversationMode){
            case TalkMode:
//                TODO
                break;
            case SingleMode:
                singleMode.singleTalk(input);
                break;
        }
    }

    public void changeSingleMode(String ID){
        if(this.singleMode.targetSingleMode(ID)) conversationMode = ConversationMode.SingleMode;
    }

    public void leaveSingleMode(){
        serviceManager.restoreReplyMarkup();
        conversationMode = ConversationMode.TalkMode;
    }

    public void singleModeRegister(@NotNull Object plugin,@NotNull SingleModeInterface singleModeInterface){
        this.singleMode.singleModeRegister(plugin, singleModeInterface);
    }

    public ConversationMode getConversationMode() {
        return conversationMode;
    }

    public void setAllReady(boolean allReady) {
        this.allReady = allReady;
    }
}

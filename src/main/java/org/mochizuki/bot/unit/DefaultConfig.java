package org.mochizuki.bot.unit;

import org.mochizuki.bot.configIO.HoconReader;

public class DefaultConfig {
    private HoconReader hoconReader;

    public DefaultConfig(HoconReader hoconReader) {
        this.hoconReader = hoconReader;
    }

    public void setDefaultConfig(){
//              Default option
        hoconReader.setValue("Bot","ServiceManager","BasicIO","type","Hocon");
        hoconReader.setValue("Bot","Global","Logger-level","INFO");
        hoconReader.setValue("Bot","Telegram","BotToken","123456789:ABCDEFGH");
        hoconReader.setValue("Bot","Telegram","BotName","BotName");
        hoconReader.getRootNode().getNode("Bot","Telegram","ChatNumber").setValue(0);

        saveFile();
    }

    public void saveFile(){
        hoconReader.serveFile();
    }
}

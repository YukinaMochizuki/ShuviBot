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

        saveFile();
    }

    public void saveFile(){
        hoconReader.serveFile();
    }
}

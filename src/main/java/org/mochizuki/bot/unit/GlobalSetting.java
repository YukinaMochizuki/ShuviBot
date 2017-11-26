package org.mochizuki.bot.unit;

public final class GlobalSetting {
    private static String loggerLevel;

    public static void setLoggerSetting(String setLoggerLevel){
        loggerLevel = setLoggerLevel;
    }

    public static String getLoggerSetting(){
        return loggerLevel;
    }



}

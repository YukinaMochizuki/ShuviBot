package org.mochizuki.bot.unit;

public final class GlobalSetting {
    private static String loggerLevel;

//     Bot setting value
    private static String botName;
    private static String botToken;

    public static void setLoggerSetting(String setLoggerLevel){
        loggerLevel = setLoggerLevel;
    }

    public static void setBotToken(String botToken) {
        GlobalSetting.botToken = botToken;
    }

    public static void setBotName(String botName) {
        GlobalSetting.botName = botName;
    }

    public static String getBotName() {
        return botName;
    }

    public static String getLoggerSetting(){
        return loggerLevel;
    }

    public static String getBotToken() {
        return botToken;
    }
}

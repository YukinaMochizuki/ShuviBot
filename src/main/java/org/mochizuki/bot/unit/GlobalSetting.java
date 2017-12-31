package org.mochizuki.bot.unit;

public final class GlobalSetting {
    private static String loggerLevel;

//     Bot setting value
    private static String botName;
    private static String botToken;
    private static long ChatNumber;

    public static void setLoggerSetting(String setLoggerLevel){
        loggerLevel = setLoggerLevel;
    }

    public static void setBotToken(String botToken) {
        GlobalSetting.botToken = botToken;
    }

    public static void setBotName(String botName) {
        GlobalSetting.botName = botName;
    }

    public static void setChatNumber(long chatNumber) {
        ChatNumber = chatNumber;
    }

    public static long getChatNumber() {
        return ChatNumber;
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

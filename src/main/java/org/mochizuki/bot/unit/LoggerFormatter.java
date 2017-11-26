package org.mochizuki.bot.unit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter{
    public String format(LogRecord logRecord) {
        Date time = new Date(logRecord.getMillis());
        SimpleDateFormat time_format = new SimpleDateFormat("hh:mm:ss");

        return "\n[" + time_format.format(time) + "]" +
                "[" + logRecord.getLoggerName() + "/" + logRecord.getLevel() + "]:" +
                logRecord.getMessage();
    }
}

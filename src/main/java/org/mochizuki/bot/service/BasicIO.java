package org.mochizuki.bot.service;

import org.mochizuki.bot.service.unit.HoconType;
import org.mochizuki.bot.service.unit.IOType;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class BasicIO {
    private Logger logger;
    private Path path;
    private IOType IO;

    BasicIO(){
        this.logger = Logger.getLogger("BasicIO");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());
    }

    public IOType getIO(){
        return IO;
    }

    void setHoconType(){
        logger.info("Data Input/Output use Hocon mode");

        this.path = Paths.get(".","value.conf");
        this.IO = new HoconType(logger,path);
    }
}

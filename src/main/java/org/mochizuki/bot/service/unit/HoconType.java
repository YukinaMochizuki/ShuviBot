package org.mochizuki.bot.service.unit;

import org.mochizuki.bot.configIO.HoconReader;

import java.nio.file.Path;
import java.util.logging.Logger;

public class HoconType implements IOType {
    private HoconReader hoconReader;
    private Logger logger;

    public HoconType(Logger logger, Path path){
        this.logger = logger;
        this.hoconReader = new HoconReader(logger).setPath(path).init();
    }

    @Override
    public boolean inputValue(String[] node, String input,boolean override) {
        int num = node.length;

        switch (num){
            case 1:
                if(!hoconReader.getValue(node[0]).equals("null") && !override){
                    return false;
                }else {
                    break;
                }
            case 2:
                if(!hoconReader.getValue(node[0],node[1]).equals("null") && !override){
                    return false;
                }else {
                    break;
                }
            case 3:
                if(!hoconReader.getValue(node[0],node[1],node[2]).equals("null") && !override){
                    return false;
                }else {
                    break;
                }
            case 4:
                if(!hoconReader.getValue(node[0],node[1],node[2],node[3]).equals("null") && !override){
                    return false;
                }else {
                    break;
                }
            case 5:
                if(!hoconReader.getValue(node[0],node[1],node[2],node[3],node[4]).equals("null") && !override){
                    return false;
                }else {
                    break;
                }
        }

        switch (num){
            case 1: hoconReader.setValue(node[0],input);
            break;
            case 2: hoconReader.setValue(node[0],node[1],input);
            break;
            case 3: hoconReader.setValue(node[0],node[1],node[2],input);
            break;
            case 4: hoconReader.setValue(node[0],node[1],node[2],node[3],input);
            break;
            case 5: hoconReader.setValue(node[0],node[1],node[2],node[3],node[4],input);
        }
        return true;
    }

    @Override
    public String getValue(String[] node, String default_value) {
        int num = node.length;
        String output = "";

        switch (num){
            case 1: output = hoconReader.getValue(node[0]);
            break;
            case 2: output = hoconReader.getValue(node[0],node[1]);
            break;
            case 3: output = hoconReader.getValue(node[0],node[1],node[2]);
            break;
            case 4: output = hoconReader.getValue(node[0],node[1],node[2],node[3]);
            break;
            case 5: output = hoconReader.getValue(node[0],node[1],node[2],node[3],node[4]);
        }

        if (output.equals("null"))return default_value;
        else return output;
    }
    public HoconReader getHoconReader() {
        return hoconReader;
    }
}

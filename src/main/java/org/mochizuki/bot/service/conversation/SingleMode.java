package org.mochizuki.bot.service.conversation;

import java.util.ArrayList;

public class SingleMode {
    private SingleModeUnit singleModeUnit;
    private ArrayList<SingleModeUnit> singleModeUnitArrayList = new ArrayList<>();

    public SingleMode(){

    }

    public boolean targetSingleMode(String ID){
        for(SingleModeUnit singleModeUnit : singleModeUnitArrayList){
            if(singleModeUnit.getId().compareTo(ID) == 0){
                this.singleModeUnit = singleModeUnit;
                return true;
            }
        }
        return false;
    }

    public void singleTalk(String inputMessage){
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.setOriginalMessage(inputMessage);
        if(inputMessage.startsWith("/")){
            messagePacket.setCommand(true);
            ArrayList<String> parameter = new ArrayList<>();
            if(inputMessage.contains(" ")){
                boolean doFirst = true;
                int index_start = 0;
                int index_end = 0;
                while (true){
                    index_start = inputMessage.indexOf(" ",index_start);

                    if(doFirst){
                        messagePacket.setMessage(inputMessage.substring(1,index_start));
                        doFirst = false;
                    }

                    if(index_start == -1)break;
                    else {
                        if(inputMessage.indexOf(" ",index_start + 1) != -1){
                            index_end = inputMessage.indexOf(" ",index_start + 1);
                            parameter.add(inputMessage.substring(index_start + 1,index_end));
                            index_start++;
                        }else {
                            parameter.add(inputMessage.substring(index_start + 1));
                            index_start++;
                        }
                    }
                }
            }else {
                parameter.add(inputMessage.substring(1));
            }
            messagePacket.setParameter(parameter);
        }else {
            messagePacket.setMessage(inputMessage);
        }

        singleModeUnit.singleTalk(inputMessage);
    }

    public void singleModeRegister(Object plugin,SingleModeInterface singleModeInterface){
        singleModeUnitArrayList.add(new SingleModeUnit(plugin,singleModeInterface));
    }
}

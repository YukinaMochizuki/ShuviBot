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
        singleModeUnit.singleTalk(inputMessage);
    }

    public void singleModeRegister(Object plugin,SingleModeInterface singleModeInterface){
        singleModeUnitArrayList.add(new SingleModeUnit(plugin,singleModeInterface));
    }
}

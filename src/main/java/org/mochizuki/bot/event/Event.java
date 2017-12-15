package org.mochizuki.bot.event;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class Event implements Comparable<Event>{
    private EventType eventType = EventType.NotDefine;

    private ArrayList<String> stringArrayList = null;
    private ArrayList<Integer> integerArrayList = null;
    private ArrayList<Boolean> booleanArrayList = null;

    @Override
    public int compareTo(Event o) {
        return o.eventType.ordinal() - this.eventType.ordinal();
    }

    public Event addStringArrayList(String string){
        if(stringArrayList == null)stringArrayList = new ArrayList<>();
        stringArrayList.add(string);
        return this;
    }

    public Event addIntegerArrayList(int i){
        if(integerArrayList == null)integerArrayList = new ArrayList<>();
        integerArrayList.add(i);
        return this;
    }

    public Event addBooleanArrayList(Boolean aBoolean){
        if(booleanArrayList == null)booleanArrayList = new ArrayList<>();
        booleanArrayList.add(aBoolean);
        return this;
    }


    public Event setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public ArrayList<Boolean> getBooleanArrayList() {
        return booleanArrayList;
    }

    public ArrayList<Integer> getIntegerArrayList() {
        return integerArrayList;
    }

    public EventType getEventType() {
        return eventType;
    }

}

package org.mochizuki.bot.service.manager.struct.unit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Milestones implements Comparator<Milestones> {
    private int ID;
    private int isDone = 0;
    private String name;
    private ArrayList<String> content;

    public Milestones(String name,int ID){
        this.name = name;
        this.ID = ID;
    }

    @Override
    public int compare(Milestones o1, Milestones o2) {
        return o1.ID - o2.ID;
    }

    public Optional<ArrayList<String>> getContent() {
        return content == null ? Optional.empty() : Optional.of(this.content);
    }

    public void setContent(String content) {
        if(this.content == null)this.content = new ArrayList<String>();
        this.content.add(content);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIsDone(int isDone){
        this.isDone = isDone;
    }

    public int getIsDone() {
        return isDone;
    }

    public String getName() {
        return name;
    }

}

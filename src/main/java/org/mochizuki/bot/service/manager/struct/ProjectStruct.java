package org.mochizuki.bot.service.manager.struct;

import org.mochizuki.bot.service.manager.struct.unit.Milestones;
import org.mochizuki.bot.service.manager.struct.unit.Work;

import java.util.ArrayList;
import java.util.Comparator;

public class ProjectStruct implements Comparator<ProjectStruct> {
    private int ID;
    private String name;
    private int level = 10;
    private String introduction = "";
    private ArrayList<Milestones> milestonesArrayList = new ArrayList<Milestones>();
    private ArrayList<Work> workArrayList = new ArrayList<Work>();

    public ProjectStruct(String name,int ID){
        this.name = name;
        milestonesArrayList = new ArrayList<Milestones>();
        workArrayList = new ArrayList<Work>();
    }

    @Override
    public int compare(ProjectStruct o1, ProjectStruct o2) {
        return o1.ID - o2.ID;
    }

    public ProjectStruct setMilestones(String milestones,int ID){
        milestonesArrayList.add(new Milestones(milestones,ID));
        return this;
    }

    public ProjectStruct setIntroduction(String introduction){
        this.introduction = introduction;
        return this;
    }

    public ProjectStruct setLevel(int level){
        this.level = level;
        return this;
    }

    public ProjectStruct setWork(String work,int ID){
        workArrayList.add(new Work(work,ID));
        return this;
    }

    public String getName(){
        return this.name;
    }

    public int getLevel(){
        return this.level;
    }

    public String getIntroduction(){
        return this.introduction;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Milestones> getMilestones(){
        return milestonesArrayList;
    }

    public ArrayList<Work> getWork() {
        return this.workArrayList;
    }
}

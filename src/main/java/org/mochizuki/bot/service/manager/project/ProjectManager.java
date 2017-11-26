package org.mochizuki.bot.service.manager.project;

import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.service.BasicIO;
import org.mochizuki.bot.service.ServiceManager;
import org.mochizuki.bot.service.manager.Service;
import org.mochizuki.bot.service.manager.struct.ProjectStruct;
import org.mochizuki.bot.service.manager.struct.unit.Milestones;
import org.mochizuki.bot.service.manager.struct.unit.Work;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class ProjectManager extends Service {
    private BasicIO basicIO;
    private Logger logger;
    private Telegram telegram;

    private ArrayList<ProjectStruct> projectStructArrayList;

    public ProjectManager(ServiceManager serviceManager){
        this.logger = Logger.getLogger("ProjectManager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.basicIO = serviceManager.getBasicIO();
        this.telegram = serviceManager.getTelegram();
    }

    @Override
    public void conversationThreadManager(String[] strings) {

    }

    public ProjectManager init(){
        this.projectStructArrayList = new ArrayList<ProjectStruct>();
        boolean nullImportData = importProject();
        if(!nullImportData)logger.info("There is no data need to import");
        else logger.info("Import data has been successfully completed");
        return this;
    }

    private void menu(){

    }

    private boolean importProject(){
        String[] node = {"Service","ProjectManager","Project0",""};
        int num = 0;
        String id = "ID";
        String name = "name";
        String introduction = "introduction";
        String level = "level";
        String workStr = "work";
        String milestonesStr = "milestones";
        String content = "content"; //TODO

        node[3] = name;

        if(basicIO.getIO().getValue(node,"null").equals("null"))return false;

        while (!basicIO.getIO().getValue(node,"null").equals("null")){
            node[2] = "Project" + num;
            node[3] = name;

            projectStructArrayList.add(new ProjectStruct(basicIO.getIO().getValue(node,"null"),
                    num));

            ProjectStruct projectStruct = projectStructArrayList.get(num);

            node[3] = level;
            if (!basicIO.getIO().getValue(node,"null").equals("null"))
                projectStruct.setLevel(Integer.parseInt(basicIO.getIO().getValue(node,"5")));

            node[3] = introduction;
            if(!basicIO.getIO().getValue(node,"null").equals("null"))
                projectStruct.setIntroduction(basicIO.getIO().getValue(node,"null"));

            projectStruct.setID(num);

            int work_num = 0;
            String[] node_L2 = Arrays.copyOf(node,node.length + 2);
            node_L2[4] = workStr + work_num;
            node_L2[5] = name;
            while (!basicIO.getIO().getValue(node_L2,"null").equals("null")){
                ArrayList<Work> workArrayList = projectStruct.getWork();
                workArrayList.add(new Work(basicIO.getIO().getValue(node_L2,"null"),work_num));

                Work work = workArrayList.get(work_num);

                work_num++;

//                              TODO content
            }

            int milestones_num = 0;
            node_L2[4] = milestonesStr + milestones_num;
            node_L2[5] = name;
            while (!basicIO.getIO().getValue(node_L2,"null").equals("null")){
                ArrayList<Milestones> milestonesArrayList = projectStruct.getMilestones();
                milestonesArrayList.add(new Milestones(basicIO.getIO().getValue(node_L2,"null"),milestones_num));

                Milestones milestones = milestonesArrayList.get(milestones_num);

                work_num++;

//                              TODO content
            }


            num++;
        }

        return true;
    }

    public void addProject(){


    }



    private void setName(){

    }
}

package org.mochizuki.bot.io;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.mochizuki.bot.Bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.logging.Logger;

//
public class HoconReader {
    private Path Path;
    private Logger logger;
    private CommentedConfigurationNode rootNode;
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    public HoconReader(Logger logger){
        this.logger = logger;
    }

//  Don't use!
    @Deprecated
    public void setValue(String[] node,String value){
        CommentedConfigurationNode targetNode = rootNode;
        for(int n = 0;n < node.length;n++){
            targetNode = rootNode.getNode(node[n]);
        }
        targetNode.setValue(value);
    }

    public void setValue(String node1,String value){
        rootNode.getNode(node1).setValue(value);
    }

    public void setValue(String node1,String node2,String value){
        rootNode.getNode(node1,node2).setValue(value);
    }

    public void setValue(String node1,String node2,String node3,String value){
        rootNode.getNode(node1,node2,node3).setValue(value);
    }

    public void setValue(String node1,String node2,String node3,String node4,String value){
        rootNode.getNode(node1,node2,node3,node4).setValue(value);
    }

    public void setValue(String node1,String node2,String node3,String node4,String node5,String value){
        rootNode.getNode(node1,node2,node3,node4,node5).setValue(value);
    }

//  Don't use!
    @Deprecated
    public String getValue(String[] node){
        CommentedConfigurationNode targetNode = rootNode;
        for(int n = 0;n < node.length;n++){
            targetNode = rootNode.getNode(node[n]);
        }
        return String.valueOf(targetNode.getValue("null"));
    }

    public String getValue(String node1){
        return String.valueOf(rootNode.getNode(node1).getValue("null"));
    }

    public String getValue(String node1, String node2){
        return String.valueOf(rootNode.getNode(node1,node2).getValue("null"));
    }

    public String getValue(String node1,String node2,String node3){
        return String.valueOf(rootNode.getNode(node1,node2,node3).getValue("null"));
    }

    public String getValue(String node1,String node2,String node3,String node4){
        return String.valueOf(rootNode.getNode(node1,node2,node3,node4).getValue("null"));
    }

    public String getValue(String node1,String node2,String node3,String node4,String node5){
        return String.valueOf(rootNode.getNode(node1,node2,node3,node4,node5).getValue("null"));
    }


    public void serveFile(){
        try {
            loader.save(rootNode);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public HoconReader init(){
        Path config_path = Paths.get(".","config.conf");

//              System config will not to create, it will be create in Bot class
        if(!Files.exists(Path) && !Path.toString().equals(config_path.toString())){
            logger.info("Configuration file is not found, will create a new one");
            try {
                Files.createFile(Path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.loader = HoconConfigurationLoader.builder().setPath(Path).build();

        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public HoconReader setPath(Path Path){
        logger.info("The Configuration file Path is" + Path);
        this.Path = Path;
        return this;
    }
}

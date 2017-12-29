package org.mochizuki.bot.service;

import org.mochizuki.bot.Exception.InvokeParameterException;
import org.mochizuki.bot.service.Annotation.SystemCommandAnnotation;
import org.mochizuki.bot.service.command.CommandIndexUnit;
import org.mochizuki.bot.service.command.SystemCommand;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CommandManager {
    private BasicIO basicIO;
    private Logger logger;

    private SystemCommand systemCommand;
    private ServiceManager serviceManager;

    private ArrayList<CommandIndexUnit> commandIndexUnitArrayList = new ArrayList<>();

    private static CommandManager commandManager;

    public CommandManager(ServiceManager serviceManager){
        this.logger = Logger.getLogger("Command Manager");
        this.serviceManager = serviceManager;
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.basicIO = serviceManager.getBasicIO();
    }

    public CommandManager init(){
        setCommandManager(this);
        this.systemCommand = new SystemCommand(serviceManager);

        return this;
    }

    public CommandManager addCommandManager(Method method ,Object object){
        commandIndexUnitArrayList.add(new CommandIndexUnit(method, object));
        return commandManager;
    }

    public CommandManager indexSystemCommand(){
        this.systemCommand = new SystemCommand(serviceManager);
        Class systemCommandClass = SystemCommand.class;
        Method[] methods = systemCommandClass.getMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(SystemCommandAnnotation.class)){
                commandIndexUnitArrayList.add(new CommandIndexUnit(method,systemCommand));
            }
        }
        return this;
    }

    private static void setCommandManager(CommandManager inputCommandManager){
        if(commandManager != null)commandManager = inputCommandManager;
    }

    public void cellCommand(String command,String ... parameter){

//        Class commandClassInfo= SystemCommand.class;
//        Method[] methods = commandClassInfo.getMethods();
//        for(Method method : methods){
//            if(method.getName().compareTo("command_".concat(command)) == 0){
//                try {
//                    method.invoke(serviceManager, (Object) null);
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

      if(parameter.length == 0){
          for(CommandIndexUnit commandIndexUnit : commandIndexUnitArrayList){
              if(commandIndexUnit.getHasParameter())continue;
              if(commandIndexUnit.getName().compareTo(command) == 0){
                  try {
                      commandIndexUnit.invokeMethod();
                      return;
                  } catch (InvokeParameterException e) {
                      e.printStackTrace();
                  } catch (NullPointerException e){
                      logger.warning("The command  " + command + " object is not inject");
                  }
              }
          }
      }else {
          for(CommandIndexUnit commandIndexUnit : commandIndexUnitArrayList){
              if(commandIndexUnit.getName().compareTo(command) == 0){
                  if(parameter.length == commandIndexUnit.getParameters().length){
                      try {
                          commandIndexUnit.invokeMethod((Object) parameter);
                          return;
                      } catch (InvokeParameterException e) {
                          e.printStackTrace();
                      } catch (NullPointerException e){
                          logger.warning("The command  " + command + " object is not inject");
                      }
                  }
              }
          }
      }
      logger.info("Command " + command + " not find");
    }


    public static CommandManager getCommandManager(){
        return commandManager;
    }
}
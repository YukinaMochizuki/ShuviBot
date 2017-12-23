package org.mochizuki.bot.service;

import org.mochizuki.bot.Exception.InjectException;
import org.mochizuki.bot.Exception.MethodListenerRegistrationError;
import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.service.Annotation.ConfigDir;
import org.mochizuki.bot.service.Annotation.Plugin;
import org.mochizuki.bot.unit.InjectBusUnit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class InjectService {
    private BasicIO basicIO;
    private PluginManagerInterface pluginManagerInterface;
    private PluginManager pluginManager;
    private ConversationManager conversationManager;
    private Telegram telegram;

    private ArrayList<InjectBusUnit> injectBusUnitArrayList = new ArrayList<>();

    InjectService(ServiceManager serviceManager,PluginManager pluginManager) {
        this.basicIO = serviceManager.getBasicIO();
        this.conversationManager = serviceManager.getConversationManager();
        this.telegram = serviceManager.getTelegram();
        this.pluginManagerInterface = pluginManager;
        this.pluginManager = pluginManager;
    }

    public void startingImport(){
        for(InjectBusUnit injectBusUnit : injectBusUnitArrayList){
            if(injectBusUnit.getType().equals("Method,Object")){
                Object object = pluginManager.findPluginObject(injectBusUnit.getMethod().getDeclaringClass().getName());

                try {
                    injectBusUnit.getMethod().invoke(object,injectBusUnit.getObject());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else if(injectBusUnit.getType().equals("Field,Object")){
                Object object = pluginManager.findPluginObject(injectBusUnit.getField().getDeclaringClass().getName());

                try {
                    injectBusUnit.getField().set(object, injectBusUnit.getObject());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
//            TODO  Use passing the target class to implement Dependency Injection
        }
    }

    public void Inject(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        try {
            if (parameterTypes.length >= 2) throw new InjectException(method.getName(),method.getDeclaringClass().
                    getAnnotation(Plugin.class).id(),"Inject only can having one parameter");

            if(parameterTypes.length == 0) throw new InjectException(method.getName(),method.getDeclaringClass().
                    getAnnotation(Plugin.class).id(),"This Inject does not having parameter");
        } catch (InjectException e) {
            e.printStackTrace();
        }

//              BasicIO
        if(parameterTypes[0].getName().equals(BasicIO.class.getName()))injectBusUnitArrayList.add(new InjectBusUnit(method,basicIO));

//              PluginManager
        else if(parameterTypes[0].getName().equals(PluginManagerInterface.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(method,pluginManagerInterface));
//              ConversationManager
        else if(parameterTypes[0].getName().equals(ConversationManager.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(method,conversationManager));
//              Telegram
        else if(parameterTypes[0].getName().equals(Telegram.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(method,telegram));
//              Logger
        else if (parameterTypes[0].getName().equals(Logger.class.getName()))
            injectBusUnitArrayList.add(new
                    InjectBusUnit(method,Logger.getLogger(method.getDeclaringClass().getAnnotation(Plugin.class).id())));
//              ConfigDir
        else if (parameterTypes[0].getName().equals(Path.class.getName())){
            if(method.isAnnotationPresent(ConfigDir.class)){
                if(method.getAnnotation(ConfigDir.class).sharedRoot()){
                    injectBusUnitArrayList.add(new
                            InjectBusUnit(method,Paths.get(".",method.getDeclaringClass().getAnnotation(Plugin.class).id())));
                }else {
                    injectBusUnitArrayList.add(new
                            InjectBusUnit(method,Paths.get(".",method.getDeclaringClass().getAnnotation(Plugin.class).id(),
                            method.getDeclaringClass().getAnnotation(Plugin.class).id() + ".conf")));
                }
            }else injectBusUnitArrayList.add(new
                    InjectBusUnit(method,Paths.get(".",method.getDeclaringClass().getAnnotation(Plugin.class).id(),
                    method.getDeclaringClass().getAnnotation(Plugin.class).id() + ".conf")));
        }

//            TODO  Use passing the target class to implement Dependency Injection
//            TODO Move to Inject class
    }

    public void Inject(Field field) {
        Class<?> fieldComponentType = field.getType();

        try {
            if (fieldComponentType == null) throw new InjectException(field.getName(),field.getDeclaringClass().
                    getAnnotation(Plugin.class).id(),"Listener only can having one parameter");
        } catch (InjectException e) {
            e.printStackTrace();
        }

        assert fieldComponentType != null;
//              BasicIO
        if(fieldComponentType.getName().equals(BasicIO.class.getName()))injectBusUnitArrayList.add(new InjectBusUnit(field,basicIO));

//              PluginManager
        else if(fieldComponentType.getName().equals(PluginManagerInterface.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(field,pluginManagerInterface));
//              ConversationManager
        else if(fieldComponentType.getName().equals(ConversationManager.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(field,conversationManager));
//              Telegram
        else if(fieldComponentType.getName().equals(Telegram.class.getName()))
            injectBusUnitArrayList.add(new InjectBusUnit(field,telegram));
//              Logger
        else if(fieldComponentType.getName().equals(Logger.class.getName()))
            injectBusUnitArrayList.add(new
                    InjectBusUnit(field,Logger.getLogger(field.getDeclaringClass().getAnnotation(Plugin.class).id())));
//              ConfigDir
        else if (fieldComponentType.getName().equals(Path.class.getName())){
            if(field.isAnnotationPresent(ConfigDir.class)){
                if(field.getAnnotation(ConfigDir.class).sharedRoot()){
                    injectBusUnitArrayList.add(new
                            InjectBusUnit(field,Paths.get(".",field.getDeclaringClass().getAnnotation(Plugin.class).id())));
                }else {
                    injectBusUnitArrayList.add(new
                            InjectBusUnit(field,Paths.get(".",field.getDeclaringClass().getAnnotation(Plugin.class).id(),
                            field.getDeclaringClass().getAnnotation(Plugin.class).id() + ".conf")));
                }
            }else injectBusUnitArrayList.add(new
                    InjectBusUnit(field,Paths.get(".",field.getDeclaringClass().getAnnotation(Plugin.class).id(),
                    field.getDeclaringClass().getAnnotation(Plugin.class).id() + ".conf")));
        }

//            TODO  Use passing the target class to implement Dependency Injection
//            TODO Move to Inject class
    }


    public void importInjection(){
        for (InjectBusUnit injectBusUnit : injectBusUnitArrayList){

        }
    }
}

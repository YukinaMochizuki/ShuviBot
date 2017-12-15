package org.mochizuki.bot.service;

import org.mochizuki.bot.Exception.MethodListenerRegistrationError;
import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventBusUnit;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Plugin.PluginInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventManager {
    private boolean isSettingUp = false;

    private EventRegister eventRegister = new EventRegister();
    private PluginManager pluginManager;
    private ArrayList<PluginInfo> pluginInfoArrayList;
    private ArrayList<EventBusUnit> eventBusUnitArrayList;

    EventManager(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        this.pluginInfoArrayList = pluginManager.getPluginInfoArrayList();
    }

    public void registerListener(Method method){
        try {
            eventRegister.registerListener(method);
        } catch (MethodListenerRegistrationError methodListenerRegistrationError) {
            methodListenerRegistrationError.printStackTrace();
        }
//        TODO Error massage
    }

    public void settingUpListener(){
        eventBusUnitArrayList = eventRegister.getEventBusUnitArrayList();
        isSettingUp = true;
    }

    public void post(Event event){
        if(isSettingUp){
            EventType eventType = event.getEventType();
            for(EventBusUnit eventBusUnit : eventBusUnitArrayList){
                if(eventBusUnit.getEventType().equals(eventType)){
                    if(eventBusUnit.getPluginInfo() != null){
                        try {
                            eventBusUnit.getMethod().invoke(eventBusUnit.getPluginInfo().getObject(),event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            //        TODO Error massage
                        }
                    }else {
                        Object object = pluginManager.findPluginObject(eventBusUnit.getMethod().getDeclaringClass().getName());
                        try {
                            eventBusUnit.getMethod().invoke(object,event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            //        TODO Error massage
                        }
                    }
                }
            }
        }

    }
}

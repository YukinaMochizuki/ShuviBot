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
    private ArrayList<EventBusUnit> eventBusUnitArrayList;

    EventManager(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }

    public void registerListener(Method method){
        try {
            eventRegister.registerListener(method);
        } catch (MethodListenerRegistrationError methodListenerRegistrationError) {
            methodListenerRegistrationError.printStackTrace();
        }
//        TODO Error massage
    }

    public void registerListener(EventType eventType,Method method){
        try {
            eventRegister.registerListener(eventType, method);
        } catch (MethodListenerRegistrationError methodListenerRegistrationError) {
            methodListenerRegistrationError.printStackTrace();
        }
//        TODO Error massage
    }

    public void registerListener(EventType eventType, Method method, Object object){
        try {
            eventRegister.registerSystemListener(eventType, method, object);
        } catch (MethodListenerRegistrationError methodListenerRegistrationError) {
            methodListenerRegistrationError.printStackTrace();
        }
//        TODO Error massage
    }
//    TODO↓
//    public void registerListener(String eventType,Method method){
//
//    }

    public void settingUpListener(){
        eventBusUnitArrayList = eventRegister.getEventBusUnitArrayList();
        isSettingUp = true;
    }

    public void post(Event event){
        if(isSettingUp){
            EventType eventType = event.getEventType();
            for(EventBusUnit eventBusUnit : eventBusUnitArrayList){
                if(eventBusUnit.getEventType().equals(eventType ) && eventBusUnit.getEventType() != EventType.NotDefine){
                    if(eventBusUnit.getPluginInfo() != null){
                        try {
                            eventBusUnit.getMethod().invoke(eventBusUnit.getPluginInfo().getObject(),event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            //        TODO Error massage
                        }
                    }else if(eventBusUnit.getObject() != null) {
                        try {
                            eventBusUnit.getMethod().invoke(eventBusUnit.getObject(),event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            //        TODO Error massage
                        }
                    }else{
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

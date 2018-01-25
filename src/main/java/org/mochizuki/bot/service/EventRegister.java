package org.mochizuki.bot.service;

import org.mochizuki.bot.Exception.MethodListenerRegistrationError;
import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventBusUnit;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Annotation.Listener;
import org.mochizuki.bot.service.Annotation.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventRegister {
    private ArrayList<EventBusUnit> eventBusUnitArrayList = new ArrayList<>();

    EventRegister(){

    }

    public void registerListener(Method method) throws MethodListenerRegistrationError{
        Class<?>[] aClass = method.getParameterTypes();

        if (aClass.length >= 2) throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                    getAnnotation(Plugin.class).id(),"Listener only can having one parameter");

        if(aClass.length == 0) throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                getAnnotation(Plugin.class).id(),"This Method does not having parameter");

        Listener listener = method.getAnnotation(Listener.class);

        if(listener.eventType().equals(EventType.NotDefine))throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                getAnnotation(Plugin.class).id(),"Listener type is not define");
        else {
            eventBusUnitArrayList.add(new EventBusUnit(method,listener.eventType()));
        }
    }

    public void registerListener(EventType eventType,Method method) throws MethodListenerRegistrationError{
        Class<?>[] aClass = method.getParameterTypes();

        if (aClass.length >= 2) throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                getAnnotation(Plugin.class).id(),"Listener only can having one parameter");

        if(aClass.length == 0)throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                getAnnotation(Plugin.class).id(),"This Method does not having parameter");



        if(eventType.compareTo(EventType.NotDefine) == 0)throw new MethodListenerRegistrationError(method.getName(),method.getDeclaringClass().
                getAnnotation(Plugin.class).id(),"Listener type is not define");
        else {
            eventBusUnitArrayList.add(new EventBusUnit(method,eventType));
        }
    }

    public void registerSystemListener(EventType eventType,Method method,Object object) throws MethodListenerRegistrationError{
        eventBusUnitArrayList.add(new EventBusUnit(method,eventType).setObject(object));
    }

    public ArrayList<EventBusUnit> getEventBusUnitArrayList() {
        return eventBusUnitArrayList;
    }

    public void registrationMethodListener(Method method, EventType eventType){
        eventBusUnitArrayList.add(new EventBusUnit(method, eventType));
    }
}

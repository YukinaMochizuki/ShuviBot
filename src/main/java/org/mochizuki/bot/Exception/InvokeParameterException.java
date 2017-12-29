package org.mochizuki.bot.Exception;

import java.lang.reflect.Method;

public class InvokeParameterException extends Exception {
    private String reason;
    private int giveParameterNumber;
    private int trueParameterNumber;
    private Class[] giveParameter;
    private Class[] trueParameter;

    public InvokeParameterException(Method method, String reason){
        this.trueParameter = method.getParameterTypes();
        this.giveParameterNumber = this.trueParameter.length;
    }

    @Override
    public String getMessage() {
        return reason;
    }

    public InvokeParameterException setGiveParameter(Class[] giveParameter) {
        this.giveParameter = giveParameter;
        return this;
    }

    public InvokeParameterException setGiveParameterNumber(int giveParameterNumber) {
        this.giveParameterNumber = giveParameterNumber;
        return this;
    }

    public InvokeParameterException setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public InvokeParameterException setTrueParameter(Class[] trueParameter) {
        this.trueParameter = trueParameter;
        return this;
    }

    public InvokeParameterException setTrueParameterNumber(int trueParameterNumber) {
        this.trueParameterNumber = trueParameterNumber;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public int getGiveParameterNumber() {
        return giveParameterNumber;
    }

    public int getTrueParameterNumber() {
        return trueParameterNumber;
    }

    public Class[] getGiveParameter() {
        return giveParameter;
    }

    public Class[] getTrueParameter() {
        return trueParameter;
    }
}

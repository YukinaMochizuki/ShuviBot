package org.mochizuki.bot.service.unit;

public interface IOType {
    public boolean inputValue(String[] node,String input,boolean override);
    public String getValue(String[] node,String default_value);
}

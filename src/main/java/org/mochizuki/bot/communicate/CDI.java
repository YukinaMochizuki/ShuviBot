package org.mochizuki.bot.communicate;

public class CDI extends Thread implements Communicate{
    private String nowCommunicate = "CDI";

    @Override
    public void run(){
//        TODO
    }

    @Override
    public String nowCommunicate() {
        return nowCommunicate;
    }
}

package org.mochizuki.bot.communicate;

import org.mochizuki.bot.service.ServiceManager;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CDI extends Thread implements Communicate{
    private String nowCommunicate = "CDI";
    ServiceManager serviceManager;

    public CDI(ServiceManager serviceManager){
        this.serviceManager = serviceManager;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            String input = scanner.nextLine();
            serviceManager.communicate(input,this);
        }
    }

    @Override
    public String nowCommunicate() {
        return nowCommunicate;
    }
}

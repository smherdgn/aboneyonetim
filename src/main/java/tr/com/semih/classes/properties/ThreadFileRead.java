package tr.com.semih.classes.properties;


import tr.com.semih.classes.beans.Abone;
import tr.com.semih.classes.logging.AbstractLogger;
import tr.com.semih.classes.logging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadFileRead implements Runnable {
    private static BufferedReader br = null;

    private AbstractLogger loggerChain = Logger.getChainOfLoggers(this.getClass());

    private List<Abone> list;
    private static File file;


    public ThreadFileRead(BufferedReader br) {
        this.br = br;
    }


    public void run() {
        String line = null;
        int count = 0;
        while (true) {


            this.list = new ArrayList<Abone>();

            try {
                while ((line = br.readLine()) != null) {
                    ReadLineToAbone readLineToAbone = new ReadLineToAbone();

                   /* Abone abone = new Abone();
                    abone.setAboneNumarası(line);*/

                    Abone abone = readLineToAbone.readAbone(line);

                    if (abone != null) {

                        loggerChain.logMessage(AbstractLogger.INFO, "Thread : " + Thread.currentThread().getName() + " " + abone.toString() + " Ekledi");


                        Global.getAboneList().add(abone);
                    }
                    if (count < 15) {
                        count++;
                    } else {
                        count = 0;
                        break;

                    }
                }
            } catch (IOException e) {
                loggerChain.logMessage(AbstractLogger.ERROR, e.getMessage());

            }

            if (line == null) {

                loggerChain.logMessage(AbstractLogger.INFO, Thread.currentThread().getName() + " İsimli Thread Kapatıldı" + "\n");


                break;
            }

        }


    }


}
package tr.com.semih.classes.properties;

import tr.com.semih.classes.logging.AbstractLogger;
import tr.com.semih.classes.logging.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Exporter {
    private StringBuilder exportedText;
    private String fileName;

    private AbstractLogger loggerChain = Logger.getChainOfLoggers(this.getClass());

    public Exporter(StringBuilder exportedText, String fileName) {

        this.exportedText = exportedText;
        this.fileName = fileName;
    }

    public Boolean export() {
        Boolean result = false;
        try {


            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(exportedText);
            writer.close();
            result = true;
        } catch (IOException e) {
            loggerChain.logMessage(AbstractLogger.ERROR, e.getMessage());
        } finally {
            return result;
        }

    }
}

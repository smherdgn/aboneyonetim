package tr.com.semih.classes.logging;

public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level, Class currentClass) {
        this.level = level;
        this.currentClass = currentClass;
    }

    @Override
    protected void write(String message) {
        System.out.println( "INFO: " + message);
    }
}
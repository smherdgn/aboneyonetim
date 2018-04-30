package tr.com.semih.classes.logging;

public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level, Class currentClass) {
        this.level = level;
        this.currentClass = currentClass;
    }

    @Override
    protected void write(String message) {
        System.err.println(currentClass.getPackage().getName() + currentClass.getName() + "-Error: " + message);
    }
}
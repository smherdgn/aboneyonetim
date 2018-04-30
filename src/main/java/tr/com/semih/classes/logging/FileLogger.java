package tr.com.semih.classes.logging;

public class FileLogger extends AbstractLogger {

    public FileLogger(int level, Class currentClass) {
        this.level = level;
        this.currentClass = currentClass;
    }

    @Override
    protected void write(String message) {
        System.out.println(currentClass.getPackage().getName() + currentClass.getName() + "-File: " + message);
    }
}
package tr.com.semih.classes.logging;

public class Logger {
    public static AbstractLogger errorLogger;
    public static AbstractLogger fileLogger;
    public static AbstractLogger consoleLogger;

    public static AbstractLogger getChainOfLoggers(Class usedClass) {

        errorLogger = new ErrorLogger(AbstractLogger.ERROR,usedClass);
        fileLogger = new FileLogger(AbstractLogger.FILE,usedClass);
        consoleLogger = new ConsoleLogger(AbstractLogger.INFO,usedClass);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }

}

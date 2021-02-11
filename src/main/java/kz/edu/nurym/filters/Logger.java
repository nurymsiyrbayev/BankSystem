package kz.edu.nurym.filters;

import java.io.IOException;
import java.util.logging.FileHandler;

public class Logger implements Runnable {
    private static FileHandler handler;
    private final java.util.logging.Logger log;
    private String string;

    @Override
    public void run() {
        addLogString(this.string);
    }

    private synchronized void addLogString(String string) {
        log.info(string);
    }

    public Logger(String className, String string) {
        this.log = java.util.logging.Logger.getLogger(className);
        this.string = string;
        this.log.setFilter(new LogFilter());
        this.log.addHandler(handler);

    }

    static {
        try {
            handler = new FileHandler("/home/azatkali/JavaProjects/bank-system/src/main/webapp/WEB-INF/logger.log", true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

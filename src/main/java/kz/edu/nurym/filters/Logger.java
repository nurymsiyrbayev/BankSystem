package kz.edu.nurym.filters;

import java.io.IOException;
import java.util.logging.FileHandler;

public class Logger implements Runnable {
    private static FileHandler handler;
    static {
        try {
            handler = new FileHandler("../src/main/resources/logs/", true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private final java.util.logging.Logger log;
    private final String string;

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


}

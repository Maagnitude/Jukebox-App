package org.hua;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
public class LogHandler {
    private static final Logger LOGGER = Logger.getLogger(LogHandler.class.getName());
    private static final String logFileName = "app.log";

    private static ExecutorService executor;

    /**
     * Saves LogRecord to Logger using ThreadPool - most used.
     *
     * @param level   of the LogRecord
     * @param message of the LogRecord
     */
    public static void writeToLog(Level level, String message) {
        if (executor == null) {    // checks if threadpool has been initialized - initializes if not.
            executor = Executors.newFixedThreadPool(6);
        }
        executor.execute(new LogSave(level, message));    // executes thread.
    }

    private static class LogSave implements Runnable {
        private static boolean logFileCreated = false;

        private Level level;
        private String message;

        public LogSave(Level level, String message) {
            this.level = level;
            this.message = message;
        }

        /**
         * Saves a logRecord to the Logger (immediately).
         *
         * @param level   of the LogRecord
         * @param message of the LogRecord
         */
        public static void writeToLogNow(Level level, String message) {
            Handler consoleHandler = null;
            Handler fileHandler = null;
            try {
                //Creating consoleHandler and fileHandler
                consoleHandler = new ConsoleHandler();

                fileHandler = new FileHandler(logFileName, logFileCreated);
                logFileCreated = true;

                //Assigning handlers to LOGGER object
                LOGGER.addHandler(consoleHandler);

                LOGGER.addHandler(fileHandler);

                //Setting levels to handlers and LOGGER
                consoleHandler.setLevel(Level.ALL);
                fileHandler.setLevel(Level.ALL);
                LOGGER.setLevel(Level.ALL);

                //Console handler removed
                LOGGER.removeHandler(consoleHandler);

                LOGGER.log(level, message);

            } catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
            }

            fileHandler.close();
        }

        @Override
        public void run() {
            writeToLogNow(level, message);
        }

    }
}

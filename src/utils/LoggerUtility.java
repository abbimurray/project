package utils;
import java.io.IOException;
import java.util.logging.*;

public class LoggerUtility {
    private static Logger logger = Logger.getLogger("ApplicationLogger");
    private static FileHandler fileHandler;

    static {
        try {
            // Setup FileHandler
            fileHandler = new FileHandler("ApplicationLogs.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error setting up logger", e);
        }
    }

    public static void log(Level level, String message, Throwable thrown) {
        logger.log(level, message, thrown);
    }

    // Log message without throwable
    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
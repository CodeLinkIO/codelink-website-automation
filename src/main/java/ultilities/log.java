package ultilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class log {
    //Initialize log4j instance
    private static final Logger log =  LogManager.getLogger(ultilities.log.class);

    //Info Level logs
    public static void info (String message) {
        log.info(message);
    }
    public static void info (Object object) {
        log.info(object);
    }

    //Warn Level logs
    public static void warn (String message) {
        log.warn(message);
    }
    public static void warn (Object object) {
        log.warn(object);
    }

    //Error Level logs
    public static void error (String message) {
        log.error(message);
    }
    public static void error (Object object) {
        log.error(object);
    }

    //Fatal Level logs
    public static void fatal (String message) {
        log.fatal(message);
    }

    //Debug Level logs
    public static void debug (String message) {
        log.debug(message);
    }
    public static void debug (Object object) {
        log.debug(object);
    }
}

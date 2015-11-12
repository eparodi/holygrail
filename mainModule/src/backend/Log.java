package backend;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;


/**
 * Represents a Log of the Game actions.
 */
public class Log {
    private Queue<String> log = new ArrayDeque<String>();
    private static Log singleInstance = new Log();

    private Log() {
    }

    /**
     * Add a String message to the Log.
     *
     * @param message Messege to be added.
     */
    public void addLog(String message) {
        log.add(message);
    }

    /**
     * Returns the first added message queued in the log.
     *
     * @return String message.
     */
    public String printLog() {
        return log.poll();
    }

    /**
     * Returns true if the Log is empty.
     *
     * @return Returns true if the Log is empty, false if not.
     */
    public boolean isEmpty() {
        return log.isEmpty();
    }

    /**
     * Returns the Log game registry object.
     *
     * @return Log object of the game registry.
     */
    public static Log getInstance() {
        return singleInstance;
    }

}

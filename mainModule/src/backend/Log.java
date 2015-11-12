package backend;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class Log {
    private Queue<String> log = new ArrayDeque<>();
    private static Log singleInstance = new Log();

    private Log() {
    }

    public void addLog(String message) {
        log.add(message);
    }

    public String printLog() {
        return log.poll();
    }

    public boolean isEmpty() {
        return log.isEmpty();
    }

    public static Log getInstance() {
        return singleInstance;
    }

}

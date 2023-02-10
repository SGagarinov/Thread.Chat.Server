package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static Logger INSTANCE = null;

    private Map<String, String> messages = new HashMap<>();

    private Logger() { }

    public static Logger getInstance() {
        if (INSTANCE == null) {
            synchronized (Logger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Logger();
                }
            }
        }
        return INSTANCE;
    }

    public void log(LocalDateTime date, String user, String msg, String path) {
        String filePath = path + "/file.log";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String text = date + ":" + user + ":" + msg + "\n";
            //Записываем всю строку
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package classes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;

public class Logger {
    private static Logger INSTANCE = null;

    private Logger() {
    }

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
            }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package classes;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {

    private Integer port;
    private String chatName;

    public Config(String path, int count) {
        try(BufferedReader reader = new BufferedReader(new FileReader(path + "/settings.config"));)
        {
            String[] configLines = new String[2];
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                configLines[i] = line;
            }

            port = Integer.parseInt(configLines[0].replace("port:", ""));
            chatName = configLines[1].replace("chat_name:", "");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}

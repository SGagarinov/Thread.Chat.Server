package classes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Set;

public class ThreadClient implements Runnable{

    private Socket clientDialog;
    private Set<ClientInfo> users;
    private String name;
    private String chatName;
    private String path;

    public ThreadClient(Socket client, Set<ClientInfo> users, String chatName, String path) {
        clientDialog = client;
        this.users = users;
        this.chatName = chatName;
        this.path = path;
    }

    void sendAll(String message) throws IOException {

        for (ClientInfo user: users) {
            try {
                DataOutputStream out = user.getOut();

                out.writeUTF(name + ": " + message);
                out.flush();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            //Записываем сообщение в лог
            Logger.getInstance().log(LocalDateTime.now(), name, message, path);
        }
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            //Добавляем клиента в список
            ClientInfo info = new ClientInfo(clientDialog, out);
            users.add(info);

            String entry = in.readUTF();
            name = entry.replace("{My name}:", "");

            String joinMessage = name + " welcome to the chat '" + chatName + "'";
            System.out.println(joinMessage);

            out.writeUTF(joinMessage);
            sendAll("joined the chat");

            while (!clientDialog.isClosed()) {
                try {
                    entry = in.readUTF();
                    //Отправка сообщения все в чате
                    sendAll(entry);

                    if (entry.equalsIgnoreCase("exit")) {
                        System.out.println("Client " + name + " initialize connections close");
                        //Убираем клиента из рассылки
                        users.remove(info);
                        break;
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    in.close();
                    out.close();
                    clientDialog.close();
                    return;
                }
            }

            System.out.println("Client " + name + " disconnected");

            in.close();
            out.close();
            clientDialog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

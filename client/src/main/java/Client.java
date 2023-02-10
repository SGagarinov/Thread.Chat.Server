import classes.Config;
import classes.Logger;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Client {

    private static String name;
    private static DataOutputStream oos;
    private static DataInputStream ois;
    private static Socket socket;

    private static String path;

    public static void main(String[] args) throws IOException, InterruptedException {

        initialClient();
        Thread sendToChatThread = new Thread(Client::sendToChat);
        Thread getMessagesThread = new Thread(Client::getMessages);

        sendToChatThread.start();
        getMessagesThread.start();

        getMessagesThread.join();
        sendToChatThread.join();
    }

    public static void initialClient() throws IOException {
        //Считываем конфиг
        System.out.println("Input files path");
        Scanner scanner = new Scanner(System.in);
        path = scanner.next();
        Config config = new Config(path, 1);

        System.out.println("Enter your name");
        name = scanner.next();
        System.out.println("Your name is " + name);

        socket = new Socket("localhost", config.getPort());
        oos = new DataOutputStream(socket.getOutputStream());
        ois = new DataInputStream(socket.getInputStream());
    }

    public static void getMessages() {
        while (!socket.isOutputShutdown()) {
            try {
                //Ответ сервера
                System.out.println(ois.readUTF());
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;
            }
        }
    }

    public static void sendToChat() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            //Отправляем серверу свой никнейм
            String clientMsg = "{My name}:" + name;
            oos.writeUTF(clientMsg);
            oos.flush();

            while (!socket.isOutputShutdown()) {

                if (br.ready()) {

                    clientMsg = br.readLine();

                    oos.writeUTF(clientMsg);
                    oos.flush();

                    //Если клиент закрыл приложение
                    if (clientMsg.equalsIgnoreCase("exit")) {
                        clientMsg = "exit";
                        oos.writeUTF(clientMsg);
                        oos.flush();

                        System.out.println("Bye bye");
                        break;
                    }
                    System.out.flush();
                    Logger.getInstance().log(LocalDateTime.now(), name, clientMsg, path);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

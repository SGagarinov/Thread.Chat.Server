import classes.ClientInfo;
import classes.Config;
import classes.ThreadClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    static ExecutorService executeIt = Executors.newFixedThreadPool(4);
    static Set<ClientInfo> users = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        //Считываем конфиг
        System.out.println("Input files path");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        Config config = new Config(path, 2);

        //Создаём и запускаем сервер
        try(ServerSocket server = new ServerSocket(config.getPort())) {

            System.out.println("Server started. Waiting clients...");
            while (!server.isClosed()) {

                //Ожидаем нового клиента
                Socket client = server.accept();
                //Запускаем отдельный поток для клиента
                executeIt.execute(new ThreadClient(client, users, config.getChatName(), path));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

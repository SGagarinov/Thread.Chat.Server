import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8888); BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server started. Waiting clients...");

            while (!server.isClosed()) {
                if (br.ready()) {

                    String serverCommand = br.readLine();
                    Socket client = server.accept();

                    executeIt.execute(new ClientHandler(client));
                    System.out.print("Connection accepted.");

                }
            }

            executeIt.shutdown();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

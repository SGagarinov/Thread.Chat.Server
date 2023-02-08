import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;

    public ClientHandler(Socket client) {
        clientDialog = client;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();

                System.out.println("READ from clientDialog message - " + entry);

                if (entry.equalsIgnoreCase("exit")) {

                    System.out.println("Buy");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    break;
                }

                out.writeUTF("Server reply - " + entry + " - OK");
                out.flush();
            }

            in.close();
            out.close();

            clientDialog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

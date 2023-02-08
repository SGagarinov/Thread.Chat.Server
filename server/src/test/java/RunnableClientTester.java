import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunnableClientTester implements Runnable{

    static Socket socket;

    public RunnableClientTester() {
        try {
            socket = new Socket("localhost", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream()); DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            int i = 0;
            // создаём рабочий цикл
            while (i < 5) {
                oos.writeUTF("clientCommand " + i);
                oos.flush();
                String in = ois.readUTF();
                System.out.println(in);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

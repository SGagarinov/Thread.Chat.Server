package classes;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadClientTest implements Runnable {

    private int id;

    public ThreadClientTest(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8888);
            System.out.println("Client connected to socket");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {

            oos.writeUTF("client " + id + " connected ");

            int i = 0;
            while (!socket.isOutputShutdown()) {
                while (i < 5) {
                    oos.writeUTF("client message: " + i);
                    oos.flush();
                    String in = ois.readUTF();
                    System.out.println(in);
                    i++;
                }
                ois.readUTF();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
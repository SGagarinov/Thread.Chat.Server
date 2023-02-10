package classes;

import java.io.DataOutputStream;
import java.net.Socket;

public class ClientInfo {
    private Socket socket;
    private DataOutputStream out;

    public ClientInfo(Socket socket, DataOutputStream out) {
        this.socket = socket;
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }
}

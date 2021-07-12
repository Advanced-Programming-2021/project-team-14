package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TaskBlock {
    private Socket socket;


    public TaskBlock(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

}

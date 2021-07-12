package server;

import model.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private static final int PORT = 7755;

    private static ThreadPool threadPool;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Database.prepareDatabase();
        runServer();
    }

    private static void runServer() {
        threadPool = new ThreadPool(2);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            log("listening on port " + PORT);
            while (true){
                threadPool.process(serverSocket.accept());
                log("accepted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String string) {
        System.out.println("\u001B[31m" + LocalDate.now() + " | " + System.currentTimeMillis() + "\u001B[0m : " + string);
    }


}

package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private int port;
    private ClientHandler clientHandler;

    public MyServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                InputStream inFromClient = clientSocket.getInputStream();
                OutputStream outToClient = clientSocket.getOutputStream();
                clientHandler.handleClient(inFromClient, outToClient);
                clientSocket.close();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        clientHandler.close();
    }
}
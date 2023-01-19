//package test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketException;
//import java.net.SocketTimeoutException;
//
//public class MyServer {
//    private int port;
//    private ClientHandler clientHandler;
//
//    private ServerSocket serverSocket;
//    private volatile boolean stop;
//    public MyServer(int port, ClientHandler clientHandler) {
//        this.port = port;
//        this.clientHandler = clientHandler;
//        this.stop=false;
//        try {
//            this.serverSocket = new ServerSocket(port);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public void runServer() throws Exception {
//
//        serverSocket.setSoTimeout(1000);
//        while (!stop) {
//            try {
//                Socket clientSocket = serverSocket.accept();
//                try {
//                    InputStream inFromClient = clientSocket.getInputStream();
//                    OutputStream outToClient = clientSocket.getOutputStream();
//                    clientHandler.handleClient(inFromClient, outToClient);
//                    clientSocket.close();
//                } catch (IOException e) {e.printStackTrace();}
//            } catch (IOException e) {throw new RuntimeException(e);}
//        }
//    }
//
//    public void start(){
//        new Thread(()-> {
//            try {
//                runServer();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//    }
//
//        public void close () {
//            stop = true;
//            try {
//                clientHandler.close();
//                serverSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    // Close InputStream and OutputStream of the current client
//                    inFromClient.close();
//                    outToClient.close();
//                    // Close the socket of the current client
//                    clientSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//

//package test;
//import java.io.*;
//import java.net.*;
//import java.sql.Connection;
//
//public class MyServer {
//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private int port;
//    private ClientHandler handler;
//    private volatile boolean ConnectionSerAndCli;
//    private volatile boolean stop;
//    public MyServer(int port, ClientHandler handler) {
//        this.port = port;
//        this.handler = handler;
//        this.stop = false;
//        this.ConnectionSerAndCli = false;
//    }
//
//    public void runServer() throws IOException {
//        serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(1000);
//        while (!stop) {
//            try {
//                clientSocket = serverSocket.accept();
//                InputStream inFromClient = clientSocket.getInputStream();
//                OutputStream outToClient = clientSocket.getOutputStream();
//                this.ConnectionSerAndCli =true;
//                try {
//                    handler.handleClient(inFromClient, outToClient);
//                } finally {
//                    inFromClient.close();
//                    outToClient.close();
//                }
//                handler.close();
//                this.ConnectionSerAndCli =false;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public void start(){
//        new Thread(()-> {
//            try {
//                runServer();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//    }
//    public void close() {
//        if (!this.ConnectionSerAndCli){
//            this.stop = true;
//            try {
//                clientSocket.close();
//                serverSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                serverSocket = null;
//                clientSocket = null;
//            }
//        }
//    }
//}

package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    int port;
    boolean stop;
    ClientHandler ch;

    public MyServer(int port, ClientHandler ch){
        this.port=port;
        this.ch= ch;
    }

    public void start(){
        stop=false;
        new Thread(()->startServer()).start();
    }
    private void startServer(){
        try{
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);

            while (!stop){
                try{
                    Socket client = server.accept();
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();

                }catch (SocketTimeoutException e){ }
            }
            server.close();

        }catch (IOException e){e.printStackTrace();}
    }

    public void close(){stop=true;}
}


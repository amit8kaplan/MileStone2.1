//package test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Arrays;
//
//public class BookScrabbleHandler {
//    private Socket clientSocket;
//    private DictionaryManager dictionaryManager;
//
//    public BookScrabbleHandler() {
//    }
//
//    public void handle(Socket clientSocket) {
//        this.clientSocket = clientSocket;
//        this.dictionaryManager = DictionaryManager.get();
//        try (
//                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//        ) {
//            String inputLine = in.readLine();
//            String[] inputWords = inputLine.split(",");
//            char queryType = inputWords[0].charAt(0);
//            String[] books = Arrays.copyOfRange(inputWords, 1, inputWords.length - 1);
//            String word = inputWords[inputWords.length - 1];
//            boolean isExist = false;
//            if (queryType == 'Q') {
//                isExist = dictionaryManager.query(books, word);
//            } else if (queryType == 'C') {
//                isExist = dictionaryManager.challenge(books, word);
//            }
//            out.println(isExist);
//            clientSocket.close();
//        } catch (IOException e) {
//            System.out.println("Error handling client: " + e);
//        }
//    }
//}


package test;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class BookScrabbleHandler implements ClientHandler  {
    private DictionaryManager dictionaryManager;
    public BookScrabbleHandler() {
        this.dictionaryManager = DictionaryManager.get();
    }
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
                PrintWriter out = new PrintWriter(outToClient, true);
        ) {
            String inputLine = in.readLine();
            String[] inputWords = inputLine.split(",");
            char queryType = inputWords[0].charAt(0);
            String[] books = Arrays.copyOfRange(inputWords, 1, inputWords.length );
            String word = inputWords[inputWords.length-1 ];
            boolean isExist = false;
            if (queryType == 'Q') {
                isExist = dictionaryManager.query(books);
            } else if (queryType == 'C') {
                isExist = dictionaryManager.challenge(books);
            }
            out.println(isExist);
        } catch (IOException e) {
            System.out.println("Error handling client: " + e);
        }
    }
    public void close() {}
}

package org.Game2D.engine.network.tcp;

import java.io.*;
import java.net.*;

public class TcpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("=== SERVER START === ");

        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server läuft auf Port " + port);
        System.out.println("Warte auf Client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client verbunden!");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("HALLO VOM SERVER!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String clientMessage = in.readLine();
        System.out.println("Client sagt: " + clientMessage);

        clientSocket.close();
        serverSocket.close();
        System.out.println("Server beendet.");
    }
}
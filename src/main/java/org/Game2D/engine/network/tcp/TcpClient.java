package org.Game2D.engine.network.tcp;

import java.io.*;
import java.net.*;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("=== CLIENT START ===");

        String serverAddress = "localhost";
        int port = 12345;

        System.out.println("Verbinde mit " + serverAddress + ":" + port);
        Socket socket = new Socket(serverAddress, port);
        System.out.println("Verbunden!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        String serverMessage = in.readLine();
        System.out.println("Server sagt: " + serverMessage);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("HALLO VOM CLIENT!");

        socket.close();
        System.out.println("Client beendet.");
    }
}
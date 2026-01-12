package org.Game2D.engine.network.udp;

import java.net.*;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        System.out.println("=== UDP SERVER START ===");

        int port = 12346;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];

        System.out.println("UDP Server läuft auf Port " + port);

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("UDP von " + clientAddress + ":" + clientPort + ": " + message);

            String response = "UDP-ACK: " + message;
            byte[] responseData = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData, responseData.length, clientAddress, clientPort);
            socket.send(responsePacket);
        }
    }
}
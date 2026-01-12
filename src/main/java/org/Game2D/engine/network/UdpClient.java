package org.Game2D.engine.network;

import java.net.*;

public class UdpClient {
    public static void main(String[] args) throws Exception {
        System.out.println("=== UDP CLIENT START ===");

        String serverAddress = "localhost";
        int serverPort = 12346;

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(serverAddress);

        String message = "HALLO UDP!";
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, address, serverPort);

        System.out.println("Sende UDP: " + message);
        socket.send(sendPacket);

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("UDP Antwort: " + response);

        socket.close();
    }
}
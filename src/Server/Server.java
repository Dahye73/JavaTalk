package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private List<ClientHandler> clients;

    public Server() {
        clients = new ArrayList<>();
    }

    public void start(int port) {
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(port);
            System.out.println("Server is running...");

            while (true) {
                Socket socket = listener.accept();
                System.out.println("New client connected.");

                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                    System.out.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }

    public synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender && client.roomId.equals(sender.roomId)) {
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    System.out.println("Error sending message to client: " + e.getMessage());
                }
            }
        }
    }


//    public static void main(String[] args) {
//        Server server = new Server();
//        server.start(9999);
//    }
}
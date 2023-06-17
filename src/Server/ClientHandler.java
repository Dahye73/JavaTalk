package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Server server;
    
    public String userId;
    public String roomId;
    
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String clientMessage;
            while (true) {
                clientMessage = in.readLine();
                if (clientMessage == null || clientMessage.equals("bye")) {
                    break;
                }
                
                String[] parts = clientMessage.split(":");
                
                if (parts.length > 2) {
                    this.userId = parts[0].trim();
                    this.roomId = parts[1].trim();
                }
                
                System.out.println(clientMessage);
                server.broadcast(clientMessage, this);
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }
}
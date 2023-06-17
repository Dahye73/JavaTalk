package Client;

import java.io.*;
import java.net.*;

import com.mysql.cj.protocol.MessageListener;

public class ChatClient {

    private String serverName;
    private int serverPort;
    private Socket socket;
    
    private PrintWriter out;
    
    
    private String userId;
    private String roomId; // Added roomId variable to save room id
    
    private BufferedReader in;
    private MessageListener messageListener;
    
    
    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }
    
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
    
    public void connectToServer() {
        try {
            socket = new Socket(serverName, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server " + serverName + " on port " + serverPort);

            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                while (true) {
                    try {
                        String messageFromServer = in.readLine();
                        if (messageListener != null && messageFromServer != null) {
                            messageListener.onMessageReceived(messageFromServer);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading from server: " + e.getMessage());
                        break;
                    }
                }
            }).start();
        } catch(IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
    
    public void sendUserIdAndRoomId(String userId, String roomId) {
        this.userId = userId; // Save the userId for later use
        this.roomId = roomId; // Save the roomId for later use
    }
    
    public void sendMessageToServer(String message) {
        String formattedMessage = this.userId + ":" + this.roomId + " : " + message; // Use the saved userId and roomId here
        out.write(formattedMessage + "\n");
        out.flush();

        if (out.checkError()) {
            System.out.println("Error sending message to server");
        }
    }
    
    public void disconnectFromServer() {
        // Implement the code to disconnect from the server
        // Close any open sockets, streams, or connections
        
        // For example, if you have a socket named "serverSocket":
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}


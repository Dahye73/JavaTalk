package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//클라이언트 간에 메세지를 중계하는 역할
public class Message_Relay {
	
	private Socket clientSocket;
	private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    public Message_Relay(Socket clientSocket) {
    	
    	this.clientSocket = clientSocket;
    	
    	try {
    		inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void relayMessage(Object message) {
    	
    	try {
    		outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Object receiveMessage() {
    	
    	Object message = null;
    	
    	try {
    		message = inputStream.readObject();
    	}catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
        
    }
    
    public void close() {
    	
    	try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

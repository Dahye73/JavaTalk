package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

//클라이언트로부터 수신한 메세지를 모든 클라이언트에게 전송하는 역할
public class Message_Broadcasting {
	
	private List<Message_Relay> connectedClients;

    public Message_Broadcasting(List<Message_Relay> connectedClients) {
        this.connectedClients = connectedClients;
    }

    public void broadcastMessage(Object message) {
        for (Message_Relay client : connectedClients) {
            client.relayMessage(message);
        }
    }
}

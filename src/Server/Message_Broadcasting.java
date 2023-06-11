package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

//Ŭ���̾�Ʈ�κ��� ������ �޼����� ��� Ŭ���̾�Ʈ���� �����ϴ� ����
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

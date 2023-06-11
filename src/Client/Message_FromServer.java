package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

//Ŭ���̾�Ʈ�� ������ ���� ���޹��� �޼����� �����ϴ� ����
public class Message_FromServer {

	private ObjectInputStream inputStream;

	public Message_FromServer(Socket socket) {
		
		try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public Object receiveMessage() {
        Object message = null;
        try {
            message = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void close() {
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

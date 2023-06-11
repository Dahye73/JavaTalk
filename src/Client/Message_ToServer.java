package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


// Ŭ���̾�Ʈ�� ä�� �޼����� ������ �����ϴ� ����
public class Message_ToServer {
	
	private ObjectOutputStream outputStream;
	
	public Message_ToServer(Socket socket) {
		
		try {
			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		}catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void sendMessage(Object message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
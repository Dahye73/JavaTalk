package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

//클라이언트가 서버로 부터 전달받은 메세지를 수신하는 역할
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

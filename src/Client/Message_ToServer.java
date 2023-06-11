package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


// 클라이언트가 채팅 메세지를 서버로 전송하는 역할
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
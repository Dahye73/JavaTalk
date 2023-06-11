package Client;

import java.io.IOException;
import java.net.Socket;


//서버와의 소켓연결을 설정하고 유지하는 역할
public class Socket_Connection_to_Server {
	private Socket socket;
	
	public Socket_Connection_to_Server(String serverAddress, int serverPort) {
		
		try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void close() {
		
		try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}

package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//클라이언트의 연결을 관리
public class Client_Connection {
	private List<ClientHandler> connectedClients;
	
	public Client_Connection() {
		
		connectedClients = new ArrayList<>();
	}
	
	public void start(int port) {
		
		try {
			
			 ServerSocket serverSocket = new ServerSocket(port);
			 System.out.println("Server started on port" + port);
			 
			 while(true) {
				 
				 Socket clientSocket = serverSocket.accept();
				 System.out.println("New client connected: " + clientSocket);
				 
				 ClientHandler clientHandler = new ClientHandler(clientSocket);
	             connectedClients.add(clientHandler);
	             clientHandler.start();
			 }
		}catch (IOException e ) {
			e.printStackTrace();
		}
	}
	
	private class ClientHandler extends Thread{
		
		private Socket clientSocket;
		
		public ClientHandler(Socket clientSocket) {
			
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
		    try {
		        // 클라이언트의 요청을 처리하는 코드 작성, 예를 들어 clientSocket에서 읽기 또는 쓰기 작업을 수행할 수 있습니다.
		        // 예를 들면, 입력 및 출력 스트림을 생성할 수 있습니다:
		        InputStream inputStream = clientSocket.getInputStream();
		        OutputStream outputStream = clientSocket.getOutputStream();

		        // 스트림을 읽거나 쓰는 등의 작업을 수행할 수 있습니다.
		        // ...

		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally { 
		        try {
		            if (clientSocket != null) {
		                clientSocket.close();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        connectedClients.remove(this);
		    }
		}
	}
	
	public static void main(String[] args) {
		
		Client_Connection server = new Client_Connection();
		server.start(12346);
	}
}

package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Ŭ���̾�Ʈ�� ������ ����
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
		        // Ŭ���̾�Ʈ�� ��û�� ó���ϴ� �ڵ� �ۼ�, ���� ��� clientSocket���� �б� �Ǵ� ���� �۾��� ������ �� �ֽ��ϴ�.
		        // ���� ���, �Է� �� ��� ��Ʈ���� ������ �� �ֽ��ϴ�:
		        InputStream inputStream = clientSocket.getInputStream();
		        OutputStream outputStream = clientSocket.getOutputStream();

		        // ��Ʈ���� �аų� ���� ���� �۾��� ������ �� �ֽ��ϴ�.
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

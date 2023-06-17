package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interface.Chatting_Page;

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
		        InputStream inputStream = clientSocket.getInputStream();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		        OutputStream outputStream = clientSocket.getOutputStream();
		        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

		        Scanner scanner = new Scanner(System.in);

		        while (true) {
		            String message = reader.readLine();
		            if (message == null) {
		                // Ŭ���̾�Ʈ�� ������ ������ �� ó���� ����
		                break;
		            }

		            System.out.println("Received message from client: " + message);

		            // Ŭ���̾�Ʈ���� ������ ������ ���� �߰�
		            writer.write("Server: " + message + "\n");
		            writer.flush();

		            // �������� �޽����� �Է��Ͽ� �����ϴ� ���� �߰�
		            System.out.print("Enter message to send to client: ");
		            String response = scanner.nextLine();
		            writer.write("Server: " + response + "\n");
		            writer.flush();
		            
		        }
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

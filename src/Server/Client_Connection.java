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
		        InputStream inputStream = clientSocket.getInputStream();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		        OutputStream outputStream = clientSocket.getOutputStream();
		        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

		        Scanner scanner = new Scanner(System.in);

		        while (true) {
		            String message = reader.readLine();
		            if (message == null) {
		                // 클라이언트가 연결을 끊었을 때 처리할 로직
		                break;
		            }

		            System.out.println("Received message from client: " + message);

		            // 클라이언트에게 응답을 보내는 로직 추가
		            writer.write("Server: " + message + "\n");
		            writer.flush();

		            // 서버에서 메시지를 입력하여 전송하는 로직 추가
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

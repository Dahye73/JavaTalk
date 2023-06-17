package Interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.net.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import database.DBConnection;
import Client.ChatClient;

public class Chatting_Page extends JFrame {

    private JTextField messageField;
    private JButton sendButton;
    private String roomName;

    private JPanel panel1;
    private JScrollPane scrollPane;
    private DBConnection dbConnection = new DBConnection();
    
    private ChatClient chatClient;
    public static int roomId;
    
    public Chatting_Page(String roomName, String serverName, int serverPort) {
    	
        this.roomName = roomName;
        this.chatClient = new ChatClient(serverName, serverPort);
        chatClient.connectToServer();
        
        String userId = Login_Page.userid;
        roomId = dbConnection.getRoomId(roomName);
        chatClient.sendUserIdAndRoomId(userId, String.valueOf(roomId));
        
        chatClient.setMessageListener(this::addReceivedMessage);
        
        setTitle("Chatting_Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        Container chatting = getContentPane();
        chatting.setBackground(Color.white);
        chatting.setLayout(null);

        JLabel title = new JLabel(roomName);
        title.setForeground(new Color(64, 64, 64));
        title.setFont(new Font("Intel", Font.PLAIN, 13));
        title.setBounds(10, 0, 200, 30);
        chatting.add(title);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBackground(new Color(255, 239, 249));

        scrollPane = new JScrollPane(panel1);
        scrollPane.setBounds(10, 30, 470, 550);
        scrollPane.setPreferredSize(new Dimension(465, 550));
        chatting.add(scrollPane);
        
        JButton backButton = new JButton("�ڷ� ����");
        backButton.setBounds(100, 10, 80, 30);  // ��ġ�� ũ�� ����
        backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
        		chatClient.disconnectFromServer();
        		 
                new ChattingList_Page();  // ä�� ��� �������� ����.
                Chatting_Page.this.dispose();  // ���� ä�� �������� �ݴ´�.
            }
        });
        
        chatting.add(backButton);  // �ڷ� ���� ��ư�� �����̳ʿ� �߰�
        
        messageField = new JTextField();
        messageField.setBounds(10, 610, 350, 30);
        chatting.add(messageField);
        
        sendButton = new JButton("����");
        sendButton.setBounds(375, 610, 100, 30);
        sendButton.setBackground(new Color(255, 239, 249));
        chatting.add(sendButton);
        
        setLocationRelativeTo(null);
        setVisible(true);

        
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }
    			
    
    	
    private void sendMessage() {

            String message = messageField.getText();
            messageField.setText("");
            														
            Timestamp timestamp = getCurrentTimestamp();
            
            roomId = dbConnection.getRoomId(roomName);
            
            String userId = Login_Page.userid;
            
            dbConnection.sendMessageToChatRoom(roomId, userId, message, timestamp);
            
            chatClient.sendMessageToServer(message);
            
            addSentMessage(message, timestamp);
    }
    
    private void addSentMessage(String message, Timestamp timestamp) {
        
    	JLabel sentMessageLabel = new JLabel(message);
        sentMessageLabel.setForeground(Color.BLACK);
        sentMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
        sentMessageLabel.setAlignmentX(RIGHT_ALIGNMENT); // ���� ����
        sentMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel1.add(sentMessageLabel);
        panel1.add(Box.createVerticalStrut(5)); // �޽��� ������ ���� ����
        panel1.revalidate();
        panel1.repaint();
        
        // ��ũ�ѹٸ� �Ʒ��� �̵���Ŵ
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
    private void addReceivedMessage(String message) {
        // Do the same as addSentMessage but align to left.
        JLabel receivedMessageLabel = new JLabel(message);
        receivedMessageLabel.setForeground(Color.BLACK);
        receivedMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
        receivedMessageLabel.setAlignmentX(LEFT_ALIGNMENT); // ���� ����
        receivedMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel1.add(receivedMessageLabel);
        panel1.add(Box.createVerticalStrut(5)); // �޽��� ������ ���� ����
        panel1.revalidate();
        panel1.repaint();

        // ��ũ�ѹٸ� �Ʒ��� �̵���Ŵ
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
    private Timestamp getCurrentTimestamp() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Timestamp.valueOf(formattedDateTime);
    }
//    public static void main(String[] args) {
//        Chatting_Page.getInstance().connectToServer("localhost", 12346);
//    }
}
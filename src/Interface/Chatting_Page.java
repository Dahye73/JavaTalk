package Interface;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.List;
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
        
        chatClient.setMessageListener(this::addReceivedMessage2);
        
        List<String> usernames = dbConnection.getUserNamesByRoomId(roomId, Login_Page.userid);
        
        setTitle("Chatting_Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        Container chatting = getContentPane();
        chatting.setBackground(Color.white);
        chatting.setLayout(null);

        JLabel title = new JLabel(roomName);
        title.setForeground(new Color(64, 64, 64));
        title.setFont(new Font("Intel", Font.PLAIN, 15));
        
        List<String> usernamesStr = new ArrayList<>();
        for (String username : usernames) {
            usernamesStr.add(username);
        }
        String joinedUsernames = String.join(", ", usernamesStr);
        if (joinedUsernames.length() > 2) {
            joinedUsernames = joinedUsernames.substring(0, joinedUsernames.length()); // 마지막 쉼표와 공백 제거
        }

        title.setText(roomName + " - " + joinedUsernames);
        title.setBounds(10, 0, 400, 40);
        chatting.add(title);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBackground(new Color(255, 239, 249));

        scrollPane = new JScrollPane(panel1);
        scrollPane.setBounds(10, 50, 470, 550);
        scrollPane.setPreferredSize(new Dimension(465, 550));
        chatting.add(scrollPane);
        
        JButton backButton = new JButton("나가기");
        backButton.setFont(backButton.getFont().deriveFont(15f)); // 글꼴 크기 설정
        backButton.setBackground(Color.WHITE); // 배경색 설정
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBounds(400, 0, 100, 40);  // 위치와 크기 설정
        backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
        		chatClient.disconnectFromServer();
        		 
                new ChattingList_Page();  // 채팅 목록 페이지를 연다.
                Chatting_Page.this.dispose();  // 현재 채팅 페이지를 닫는다.
            }
        });
        
        chatting.add(backButton);  // 뒤로 가기 버튼을 컨테이너에 추가
        
        messageField = new JTextField();
        messageField.setBounds(10, 610, 350, 30);
        chatting.add(messageField);
        
        sendButton = new JButton("전송");
        sendButton.setBounds(375, 610, 100, 30);
        sendButton.setBackground(new Color(255, 239, 249));
        chatting.add(sendButton);
        
        displayChatHistory();
        
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
        JTextArea sentMessageLabel = new JTextArea(3, 10);
        sentMessageLabel.setFont(new Font("Intel", Font.PLAIN, 15)); 
        sentMessageLabel.setEditable(false);
        sentMessageLabel.setText(message + "\n" + timestamp);
        sentMessageLabel.setLineWrap(true);
        sentMessageLabel.setWrapStyleWord(true);
        sentMessageLabel.setBackground(new Color(255, 239, 249)); // 적당한 배경색으로 설정
        JPanel messageContainer = new JPanel(new BorderLayout());
        messageContainer.setBackground(new Color(255, 239, 249));
        messageContainer.add(sentMessageLabel, BorderLayout.LINE_END); // 메시지를 오른쪽으로 정렬
        messageContainer.setBorder(new EmptyBorder(5, 50, 5, 5));
        panel1.add(messageContainer);
        panel1.add(Box.createVerticalStrut(5)); // 메시지 사이의 간격 조정
        panel1.revalidate();
        panel1.repaint();

        // 스크롤바를 아래로 이동시킴
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    private void addReceivedMessage(String userId, String message, Timestamp timestamp) {
        JTextArea receivedMessageLabel = new JTextArea(3, 10);
        receivedMessageLabel.setFont(new Font("Intel", Font.PLAIN, 15));
        receivedMessageLabel.setEditable(false);
        receivedMessageLabel.setText(userId + ": " + message + "\n" + timestamp);
        receivedMessageLabel.setLineWrap(true);
        receivedMessageLabel.setWrapStyleWord(true);
        receivedMessageLabel.setBackground(new Color(255, 239, 249)); // 적당한 배경색으로 설정
        JPanel messageContainer = new JPanel(new BorderLayout());
        messageContainer.setBackground(new Color(255, 239, 249));
        messageContainer.add(receivedMessageLabel, BorderLayout.LINE_START); // 메시지를 왼쪽으로 정렬
        messageContainer.setBorder(new EmptyBorder(5, 5, 5, 50));
        panel1.add(messageContainer);
        panel1.add(Box.createVerticalStrut(5)); // 메시지 사이의 간격 조정
        panel1.revalidate();
        panel1.repaint();

        // 스크롤바를 아래로 이동시킴
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    private void addReceivedMessage2(String rawMessage) {
        String[] splitMessage = rawMessage.split(":", 3); // ':'를 기준으로 문자열 분리
        String userId = splitMessage[0];
        String message = splitMessage[2];

        JTextArea receivedMessageLabel = new JTextArea(3, 10);
        receivedMessageLabel.setFont(new Font("Intel", Font.PLAIN, 15));
        receivedMessageLabel.setEditable(false);
        receivedMessageLabel.setText(userId + ": " + message);
        receivedMessageLabel.setLineWrap(true);
        receivedMessageLabel.setWrapStyleWord(true);
        receivedMessageLabel.setBackground(new Color(255, 239, 249)); // 적당한 배경색으로 설정
        JPanel messageContainer = new JPanel(new BorderLayout());
        messageContainer.setBackground(new Color(255, 239, 249));
        messageContainer.add(receivedMessageLabel, BorderLayout.LINE_START); // 메시지를 왼쪽으로 정렬
        messageContainer.setBorder(new EmptyBorder(5, 5, 5, 50));
        panel1.add(messageContainer);
        panel1.add(Box.createVerticalStrut(5)); // 메시지 사이의 간격 조정
        panel1.revalidate();
        panel1.repaint();

        // 스크롤바를 아래로 이동시킴
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
    private void displayChatHistory() {
        List<ChatMessage> chatHistory = dbConnection.getChatHistory(roomId, Login_Page.userid);
        for (ChatMessage chatMessage : chatHistory) {
            if (chatMessage.getUserId().equals(Login_Page.userid)) {
                addSentMessage(chatMessage.getMessage(), chatMessage.getTimestamp());
            } else {
                addReceivedMessage(chatMessage.getUserId(), chatMessage.getMessage(), chatMessage.getTimestamp());
            }
        }
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
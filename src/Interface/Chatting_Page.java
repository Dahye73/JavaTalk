package Interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Chatting_Page extends JFrame {

    private JTextField messageField;
    private JButton sendButton;
    private String roomName;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private JPanel panel1;
    private JScrollPane scrollPane;

    public Chatting_Page(String roomName, String serverIP, int port) {
        this.roomName = roomName;

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

        messageField = new JTextField();
        messageField.setBounds(10, 610, 350, 30);
        chatting.add(messageField);

        sendButton = new JButton("전송");
        sendButton.setBounds(375, 610, 100, 30);
        sendButton.setBackground(new Color(255, 239, 249));
        chatting.add(sendButton);

        setLocationRelativeTo(null);
        setVisible(true);

        connectToServer(serverIP, port);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void connectToServer(String serverIP, int port) {
        try {
            socket = new Socket(serverIP, port);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 서버로부터 오는 메시지를 읽는 쓰레드 시작
            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            String message = reader.readLine();
                            if (message == null) {
                                // 서버 연결이 종료되었을 때 처리할 로직
                                break;
                            }
                            // 서버로부터 받은 메시지를 UI에 표시하는 로직 구현
                            addServerMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiveThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            String message = messageField.getText();
            writer.write(message + "\n");
            writer.flush(); // 버퍼를 비우고 데이터를 강제로 전송

            JLabel sentMessageLabel = new JLabel(message);
            sentMessageLabel.setForeground(Color.BLACK);
            sentMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
            sentMessageLabel.setAlignmentX(RIGHT_ALIGNMENT); // 우측 정렬
            sentMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.add(sentMessageLabel);
            panel1.add(Box.createVerticalStrut(5)); // 메시지 사이의 간격 조정
            panel1.revalidate();
            panel1.repaint();

            messageField.setText(""); // 메시지 입력 필드 초기화
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addServerMessage(String message) {
        JLabel serverMessageLabel = new JLabel("서버: " + message);
        serverMessageLabel.setForeground(Color.RED);
        serverMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
        serverMessageLabel.setAlignmentX(RIGHT_ALIGNMENT); // 우측 정렬
        serverMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel1.add(serverMessageLabel);
        panel1.add(Box.createVerticalStrut(5)); // 메시지 사이의 간격 조정
        panel1.revalidate();
        panel1.repaint();
        
        // 스크롤바를 아래로 이동시킴
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void setSendButtonActionListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    public String getMessageFieldText() {
        return messageField.getText();
    }

    public void clearMessageField() {
        messageField.setText("");
    }

//    public static void main(String[] args) {
//        Chatting_Page.getInstance().connectToServer("localhost", 12346);
//    }
}

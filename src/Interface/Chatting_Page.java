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

        sendButton = new JButton("����");
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

            // �����κ��� ���� �޽����� �д� ������ ����
            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            String message = reader.readLine();
                            if (message == null) {
                                // ���� ������ ����Ǿ��� �� ó���� ����
                                break;
                            }
                            // �����κ��� ���� �޽����� UI�� ǥ���ϴ� ���� ����
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
            writer.flush(); // ���۸� ���� �����͸� ������ ����

            JLabel sentMessageLabel = new JLabel(message);
            sentMessageLabel.setForeground(Color.BLACK);
            sentMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
            sentMessageLabel.setAlignmentX(RIGHT_ALIGNMENT); // ���� ����
            sentMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.add(sentMessageLabel);
            panel1.add(Box.createVerticalStrut(5)); // �޽��� ������ ���� ����
            panel1.revalidate();
            panel1.repaint();

            messageField.setText(""); // �޽��� �Է� �ʵ� �ʱ�ȭ
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addServerMessage(String message) {
        JLabel serverMessageLabel = new JLabel("����: " + message);
        serverMessageLabel.setForeground(Color.RED);
        serverMessageLabel.setFont(new Font("Intel", Font.PLAIN, 13));
        serverMessageLabel.setAlignmentX(RIGHT_ALIGNMENT); // ���� ����
        serverMessageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel1.add(serverMessageLabel);
        panel1.add(Box.createVerticalStrut(5)); // �޽��� ������ ���� ����
        panel1.revalidate();
        panel1.repaint();
        
        // ��ũ�ѹٸ� �Ʒ��� �̵���Ŵ
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

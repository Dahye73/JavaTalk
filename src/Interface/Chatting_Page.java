package Interface;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Chatting_Page extends JFrame {

	
	private JTextField messageField;
	private JButton sendButton;
	
    public Chatting_Page() {
        
    	setTitle("Chatting_Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        
        Container chatting = getContentPane();
        chatting.setBackground(Color.white);
        chatting.setLayout(null);
        
        JLabel title = new JLabel("대화상대명");
        title.setForeground(new Color(64, 64, 64));
        title.setFont(new Font("Intel", Font.PLAIN, 13));
        title.setBounds(10, 0, 200, 30);
        chatting.add(title);
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBackground(new Color(255, 239, 249));
        panel1.setBounds(10, 30, 470, 550);
        panel1.setPreferredSize(new Dimension(465, 550));
        chatting.add(panel1);
        
        messageField = new JTextField();
        messageField.setBounds(10, 610, 350, 30);
        chatting.add(messageField);

        sendButton = new JButton("전송");
        sendButton.setBounds(375, 610, 100, 30);
        sendButton.setBackground(new Color(255, 239, 249));
        chatting.add(sendButton);

        setLocationRelativeTo(null); 
        setVisible(true);
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Chatting_Page chatPage = new Chatting_Page();

                chatPage.setVisible(true);
            }
        });
    }

}

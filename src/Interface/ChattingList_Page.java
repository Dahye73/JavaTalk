package Interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Server.*;

public class ChattingList_Page extends JFrame{
	
	
    public ChattingList_Page() {
        
    	setTitle("Chatting Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        
        Container setting = getContentPane();
        setting.setLayout(null); // GridBagLayout 설정

        Color back_color = new Color(255, 255, 255);
        setting.setBackground(back_color);
        
        JLabel chatting_text = new JLabel("채팅");
        chatting_text.setForeground(new Color(64, 64, 64));
        chatting_text.setFont(new Font("Intel", Font.PLAIN, 20));
        chatting_text.setBounds(20, 0, 200, 50);
        add(chatting_text);
 
        BottomBar bottombar = new BottomBar();
        
        setting.add(bottombar);
        setLocationRelativeTo(null); 
        setVisible(true);
        
        Client_Connection server = new Client_Connection();
        
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start(12346);
            }
        });
        
        serverThread.start();

	}

	
	public static void main(String[] args) {
		new ChattingList_Page();
	}
}
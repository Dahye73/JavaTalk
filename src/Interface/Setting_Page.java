package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Setting_Page extends JFrame{
	public Setting_Page() {
		
		setTitle("Setting Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        
        Container setting = getContentPane();
        setting.setLayout(null); // GridBagLayout 설정

        Color back_color = new Color(255, 255, 255);
        setting.setBackground(back_color);
        
        JLabel setting_text = new JLabel("설정");
        setting_text.setForeground(new Color(64, 64, 64));
        setting_text.setFont(new Font("Intel", Font.PLAIN, 20));
        setting_text.setBounds(20, 0, 200, 50);
        add(setting_text);
        
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(Color.white);
        logoutButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	logout();
            }
        });
        
        logoutButton.setBounds(20, 500, 100, 30);
        setting.add(logoutButton);
        
        
        BottomBar bottombar = new BottomBar();
        
        setting.add(bottombar);
        setLocationRelativeTo(null); 
        setVisible(true);
        
	}
	
	private void logout() {
    	Login_Page.isLoginIn = false;
    	Start_Page startPage = new Start_Page();
    	startPage.setVisible(true);
    	dispose();
    }
	
	
//	public static void main(String[] args) {
//		new Setting_Page();
//	}
}

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
        setting.setLayout(new GridBagLayout()); // GridBagLayout 설정

        Color back_color = new Color(255, 255, 255);
        setting.setBackground(back_color);
        
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFocusPainted(false);
        
        logoutButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	logout();
            }
        });
        
        setting.add(logoutButton);
        setLocationRelativeTo(null); 
        setVisible(true);
        
	}
	
	private void logout() {
    	Login_Page.isLoginIn = false;
    	dispose();
    	Start_Page startPage = new Start_Page();
    	startPage.setVisible(true);
    }
	
	
	public static void main(String[] args) {
		new Setting_Page();
	}
}

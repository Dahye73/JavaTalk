package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BottomBar extends JPanel {
	
    public BottomBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(255, 255, 255));

        JLabel friendLabel = new JLabel("ģ��");
        friendLabel.setForeground(new Color(64, 64, 64));
        friendLabel.setFont(new Font("��������ڵ�", Font.PLAIN, 17));
        friendLabel.setBorder(new EmptyBorder(10, 70, 10, 120));
        
        friendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Profile_Page profilePage = PageManager.getInstance().getProfilePage();
                profilePage.setVisible(true);

            }
        });
        add(friendLabel);

        JLabel chatLabel = new JLabel("ä��");
        chatLabel.setForeground(new Color(64, 64, 64));
        chatLabel.setFont(new Font("��������ڵ�", Font.PLAIN, 17));
        chatLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        chatLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChattingList_Page chattingListPage = PageManager.getInstance().getChattingListPage();
                chattingListPage.setVisible(true);

            }
        });
        
        add(chatLabel);
        
        
        JLabel logoutLabel = new JLabel("�α׾ƿ�");
        logoutLabel.setForeground(new Color(64, 64, 64));
        logoutLabel.setFont(new Font("��������ڵ�", Font.PLAIN, 17));
        logoutLabel.setBorder(new EmptyBorder(10, 120, 10, 70));
        
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // ���� �����ִ� ��� â �ݱ�
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    window.dispose();
                }
                
                // isLoginIn ������ false�� ����
                Login_Page.isLoginIn = false;
                
                // Start_Page�� ���ư���
                Start_Page startPage = new Start_Page();
                startPage.setVisible(true);
            }
        });
        add(logoutLabel);

        setBounds(0, 610, 500, 60);
    }
    
    
}


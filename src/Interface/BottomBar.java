package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BottomBar extends JPanel {
	
    public BottomBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(255, 255, 255));

        JLabel friendLabel = new JLabel("친구");
        friendLabel.setForeground(new Color(64, 64, 64));
        friendLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        friendLabel.setBorder(new EmptyBorder(10, 70, 10, 120));
        
        friendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Profile_Page profilePage = PageManager.getInstance().getProfilePage();
                profilePage.setVisible(true);

            }
        });
        add(friendLabel);

        JLabel chatLabel = new JLabel("채팅");
        chatLabel.setForeground(new Color(64, 64, 64));
        chatLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        chatLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        chatLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChattingList_Page chattingListPage = PageManager.getInstance().getChattingListPage();
                chattingListPage.setVisible(true);

            }
        });
        
        add(chatLabel);
        
        
        JLabel logoutLabel = new JLabel("로그아웃");
        logoutLabel.setForeground(new Color(64, 64, 64));
        logoutLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        logoutLabel.setBorder(new EmptyBorder(10, 120, 10, 70));
        
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 현재 열려있는 모든 창 닫기
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    window.dispose();
                }
                
                // isLoginIn 변수를 false로 변경
                Login_Page.isLoginIn = false;
                
                // Start_Page로 돌아가기
                Start_Page startPage = new Start_Page();
                startPage.setVisible(true);
            }
        });
        add(logoutLabel);

        setBounds(0, 610, 500, 60);
    }
    
    
}


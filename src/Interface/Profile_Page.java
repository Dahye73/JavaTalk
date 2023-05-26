package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Profile_Page extends JFrame {

	
    public Profile_Page() {
        setTitle("Profile_Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE); // 배경색을 화이트로 설정

        // 상단바 생성
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(255, 255, 255));
        topBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel pageTitle = new JLabel("회원 프로필");
        pageTitle.setForeground(Color.gray);
        pageTitle.setFont(new Font("나눔스퀘어라운드 Bold", Font.BOLD, 15));
       
        topBar.add(pageTitle);
        
        JButton logoutButton = new JButton("로그아웃");
        
        logoutButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	logout();
            }
        });
        
        topBar.add(logoutButton);
        
        add(topBar, BorderLayout.NORTH);

        
        // 하단바 생성
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.setBackground(Color.white);

        JLabel friendLabel = new JLabel("친구");
        friendLabel.setForeground(Color.gray);
        friendLabel.setFont(new Font("나눔스퀘어라운드 Bold", Font.BOLD, 20));
        friendLabel.setBorder(new EmptyBorder(0, 80, 10, 0));
        bottomBar.add(friendLabel);

        // 왼쪽 여백을 조정하기 위한 Glue 추가
        bottomBar.add(Box.createHorizontalGlue());

        JLabel chatLabel = new JLabel("채팅");
        chatLabel.setForeground(Color.gray);
        chatLabel.setFont(new Font("나눔스퀘어라운드 Bold", Font.BOLD, 20));
        chatLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); 
        bottomBar.add(chatLabel);

        // 오른쪽 여백을 조정하기 위한 Glue 추가
        bottomBar.add(Box.createHorizontalGlue());

        JLabel settingsLabel = new JLabel("설정");
        settingsLabel.setForeground(Color.gray);
        settingsLabel.setFont(new Font("나눔스퀘어라운드 Bold", Font.BOLD, 20));
        settingsLabel.setBorder(new EmptyBorder(0, 0, 10, 80));
        bottomBar.add(settingsLabel);

        add(bottomBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void logout() {
    	Login_Page.isLoginIn = false;
    	dispose();
    	Start_Page startPage = new Start_Page();
    	startPage.setVisible(true);
    }
}


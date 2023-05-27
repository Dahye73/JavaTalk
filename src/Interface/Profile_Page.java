package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class Profile_Page extends JFrame {

    public Profile_Page() {
    	
        setTitle("Profile Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        getContentPane().setBackground(Color.WHITE); // 배경색을 화이트로 설정
        setLayout(null);
        
        JLabel pageTitle = new JLabel("JavaTalk");
        pageTitle.setForeground(new Color(64, 64, 64));
        pageTitle.setFont(new Font("Intel", Font.PLAIN, 20));
        pageTitle.setBounds(20, 0, 200, 50);
        add(pageTitle);
        
       
        JLabel profile_Text = new JLabel("내 프로필");
        profile_Text.setForeground(new Color(64, 64, 64));
        profile_Text.setFont(new Font("나눔고딕코딩", Font.PLAIN, 13));
        profile_Text.setBounds(20, 60, 200, 30);
        add(profile_Text);
        
        ImageIcon Myprofile_Image = new ImageIcon(Profile_Page.class.getResource("/image/person6.png"));
        UserProfileButton Myprofilebtn = new UserProfileButton(Myprofile_Image);
        Myprofilebtn.setText(Login_Page.username);
        Myprofilebtn.setBounds(20, 100, 325, 80);
        add(Myprofilebtn);
        
        JLabel Friend_Text = new JLabel("친구");
        Friend_Text.setForeground(new Color(64, 64, 64));
        Friend_Text.setFont(new Font("나눔고딕코딩", Font.PLAIN, 13));
        Friend_Text.setBounds(20, 200, 200, 30);
        add(Friend_Text);
        
        ImageIcon Friend_Image = new ImageIcon(Profile_Page.class.getResource("/image/profile1.png"));
        UserProfileButton Friendbtn = new UserProfileButton(Friend_Image);
        Friendbtn.setText("친구 1");
        Friendbtn.setBounds(20, 240, 325, 80);
        add(Friendbtn);
        
        // 하단바 생성
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.setBackground(new Color(255, 255, 255));

        JLabel friendLabel = new JLabel("친구");
        friendLabel.setForeground(new Color(64, 64, 64));
        friendLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        friendLabel.setBorder(new EmptyBorder(10, 70, 10, 120));
        bottomBar.add(friendLabel);

        JLabel chatLabel = new JLabel("채팅");
        chatLabel.setForeground(new Color(64, 64, 64));
        chatLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        chatLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); 
        bottomBar.add(chatLabel);

        JLabel settingsLabel = new JLabel("설정");
        settingsLabel.setForeground(new Color(64, 64, 64));
        settingsLabel.setFont(new Font("나눔고딕코딩", Font.PLAIN, 17));
        settingsLabel.setBorder(new EmptyBorder(10, 120, 10, 70));

        settingsLabel.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Setting_Page();
        		dispose();
        	}
        });
        
        bottomBar.add(settingsLabel);
        bottomBar.setBounds(0, 610, 500, 60);
        add(bottomBar);

        setLocationRelativeTo(null); 
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
    	new Profile_Page();
    }
}
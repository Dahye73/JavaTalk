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

        getContentPane().setBackground(Color.WHITE); // ������ ȭ��Ʈ�� ����

        // ��ܹ� ����
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(255, 255, 255));
        topBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel pageTitle = new JLabel("ȸ�� ������");
        pageTitle.setForeground(Color.gray);
        pageTitle.setFont(new Font("������������� Bold", Font.BOLD, 15));
       
        topBar.add(pageTitle);
        
        JButton logoutButton = new JButton("�α׾ƿ�");
        
        logoutButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	logout();
            }
        });
        
        topBar.add(logoutButton);
        
        add(topBar, BorderLayout.NORTH);

        
        // �ϴܹ� ����
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.setBackground(Color.white);

        JLabel friendLabel = new JLabel("ģ��");
        friendLabel.setForeground(Color.gray);
        friendLabel.setFont(new Font("������������� Bold", Font.BOLD, 20));
        friendLabel.setBorder(new EmptyBorder(0, 80, 10, 0));
        bottomBar.add(friendLabel);

        // ���� ������ �����ϱ� ���� Glue �߰�
        bottomBar.add(Box.createHorizontalGlue());

        JLabel chatLabel = new JLabel("ä��");
        chatLabel.setForeground(Color.gray);
        chatLabel.setFont(new Font("������������� Bold", Font.BOLD, 20));
        chatLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); 
        bottomBar.add(chatLabel);

        // ������ ������ �����ϱ� ���� Glue �߰�
        bottomBar.add(Box.createHorizontalGlue());

        JLabel settingsLabel = new JLabel("����");
        settingsLabel.setForeground(Color.gray);
        settingsLabel.setFont(new Font("������������� Bold", Font.BOLD, 20));
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


package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import Interface.Login_Page;
import Interface.SignUp_Page;

import javax.swing.*;

public class Start_Page extends JFrame {
    int width = 500;
    int height = 700;
    
    public Start_Page() {
        setTitle("JavaTalk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        Container c1 = getContentPane();
        
        Color back_color = new Color(255, 255, 255);
        c1.setBackground(back_color);

        GridBagLayout layout = new GridBagLayout();
        c1.setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1.0; // ���� �� ���� ������ �Ҵ��մϴ�.
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel("JavaTalk");
        label.setForeground( new Color(64, 64, 64)); 
        label.setFont(new Font("������������� Bold", Font.BOLD, 35));

        c1.add(label, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 10)); // �׸��� ���̾ƿ����� ����

        JButton loginButton = new JButton("�α���");
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        
        Dimension buttonSize = new Dimension(250, 50);
        loginButton.setPreferredSize(buttonSize);

        loginButton.setBackground(new Color(139, 193, 246)); 
        loginButton.setFont(new Font("LG Smart UI", Font.BOLD, 13));
        loginButton.setForeground(Color.white); 

        buttonPanel.add(loginButton);
        
        JButton signUpButton = new JButton("ȸ������");
        signUpButton.setBorder(null);
        signUpButton.setFocusPainted(false);
        
        Dimension signUp_buttonSize = new Dimension(250, 50);
        signUpButton.setPreferredSize(signUp_buttonSize);

        signUpButton.setBackground(new Color(139, 193, 246)); 
        signUpButton.setFont(new Font("LG smart UI", Font.BOLD, 13));
        signUpButton.setForeground(Color.white); 
        
        buttonPanel.add(signUpButton);

        constraints.gridy = 2;
        c1.add(buttonPanel, constraints);
        
        //��ư Ŭ�� �̺�Ʈ
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_Page();
                dispose(); // ���� ������ �ݱ�
            }
        });
        
        signUpButton.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		new SignUp_Page();
        		dispose();
        	}
        });
        
      //â�� ��ġ�� �߾ӿ� ����
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
    	
        new Start_Page();
        
    }
}
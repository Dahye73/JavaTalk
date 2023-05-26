package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Profile_Page extends JFrame {

    public Profile_Page() {
        setTitle("Profile_Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE); // ������ ȭ��Ʈ�� ����

        // ��ܹ� ����
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(96, 96, 96));
        topBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel pageTitle = new JLabel("ȸ�� ������");
        pageTitle.setForeground(Color.white);
        pageTitle.setFont(new Font("������������� Bold", Font.BOLD, 15));
       
        topBar.add(pageTitle);

        add(topBar, BorderLayout.NORTH);

        // �ϴܹ� ����
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.gray);
        bottomBar.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel friendLabel = new JLabel("ģ��");
        friendLabel.setForeground(Color.white);
        friendLabel.setFont(new Font("������������� Bold", Font.BOLD, 12));
        bottomBar.add(friendLabel);

        JLabel chatLabel = new JLabel("ä��");
        chatLabel.setForeground(Color.white);
        chatLabel.setFont(new Font("������������� Bold", Font.BOLD, 12));
        bottomBar.add(chatLabel);

        JLabel settingsLabel = new JLabel("����");
        settingsLabel.setForeground(Color.white);
        settingsLabel.setFont(new Font("������������� Bold", Font.BOLD, 12));
        bottomBar.add(settingsLabel);

        add(bottomBar, BorderLayout.SOUTH);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
}


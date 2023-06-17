package Interface;

import java.awt.*;
import javax.swing.*;

import Server.ServerLauncher;

import java.awt.event.*;
import database.DBConnection;

public class Login_Page extends JFrame {

	private JTextField idField;
    private JTextField passwordField;
    private DBConnection dbConnection = new DBConnection();
    
    public static String username = "�����";
    public static String userphonenumber;
    public static String useremail;
    public static String userid;
    
    public static boolean isLoginIn = false;
    
    
    public Login_Page() {

        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        Container login = getContentPane();
        login.setLayout(new GridBagLayout()); // GridBagLayout ����

        Color back_color = new Color(255, 255, 255);
        login.setBackground(back_color);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 50, 0);

        JLabel loginText = new JLabel("Login");
        loginText.setForeground(new Color(124, 124, 124));
        loginText.setFont(new Font("������������� Bold", Font.BOLD, 30));

        login.add(loginText, constraints);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.anchor = GridBagConstraints.WEST; // ���̺��� �������� ����

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.insets = new Insets(10, 10, 10, 10);
        
        addField(inputPanel, "ID", "  ���̵� �Է��ϼ���", 17, labelConstraints, fieldConstraints);
        addField(inputPanel, "Password", "  ��й�ȣ�� �Է��ϼ���", 17, labelConstraints, fieldConstraints);
        
        constraints.gridy = 2;
        login.add(inputPanel, constraints);
        
        JButton loginBtn = new JButton("�α���");
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(88, 172, 255)); // ��ư ���� ����
        loginBtn.setFont(new Font("������������� Bold", Font.BOLD, 13));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(null);
        
        Dimension loginBtn_buttonSize = new Dimension(80, 30);
        loginBtn.setPreferredSize(loginBtn_buttonSize);
        
        constraints.gridy = 3;
        login.add(loginBtn, constraints);
        
        loginBtn.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
            	String id = idField.getText();
            	String password = passwordField.getText();
            	
            	boolean isAuthenticated = dbConnection.authenticateUser(id, password);
            	
            	if(isAuthenticated) {
            		
            		
            		username = dbConnection.getUser(id, "name");
            		userphonenumber = dbConnection.getUser(id, "phonenumber");
            		useremail = dbConnection.getUser(id, "email");
            		userid = dbConnection.getUser(id, "id");
            		isLoginIn = true;
            		
            		// ���� ������ ��׶��� ������� �̵�
            		Thread serverThread = new Thread(() -> {
            		    ServerLauncher serverLauncher = new ServerLauncher(isLoginIn);
            		    serverLauncher.startServer();
            		});
            		serverThread.start();

            		
            		JOptionPane.showMessageDialog(Login_Page.this, "�α��� ����!", "�˸�", JOptionPane.INFORMATION_MESSAGE);
            		
            		System.out.println("�α��� ���� !");
            		System.out.println(userid + "," + username + "," + userphonenumber + "," + useremail);
            		Profile_Page profilePage = new Profile_Page();
            		profilePage.setVisible(true);
            		dispose();
            		
            	}else {
            		JOptionPane.showMessageDialog(Login_Page.this, "���̵� �Ǵ� ��й�ȣ�� �ùٸ��� �ʽ��ϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        
      //â�� ��ġ�� �߾ӿ� ����
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void addField(JPanel panel, String labelText, String placeholder, int columns,
            GridBagConstraints labelConstraints, GridBagConstraints fieldConstraints) {
        
    	JLabel label = new JLabel(labelText);
        label.setForeground(new Color(218, 218, 218));
        JTextField field = new JTextField(placeholder, columns);
        field.setForeground(new Color(175, 175, 175));
        field.setFont(new Font("������������� Light", Font.BOLD, 13));

        Insets fieldInsets = new Insets(10, 10, 10, 10); // ���� ����
        field.setMargin(fieldInsets);

        panel.add(label, labelConstraints);
        panel.add(field, fieldConstraints);
        
        if (labelText.equals("ID")) {
            idField = field;
        } else if (labelText.equals("Password")) {
            passwordField = field;
        }
        
    }
    
}
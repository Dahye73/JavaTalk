package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.*;

import database.DBConnection;

class RoundBorder implements Border {
    private int radius;

    public RoundBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius, this.radius, this.radius, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(101, 101, 101)); // �׵θ� �÷� ����
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

class SignUp_Page extends JFrame {
	
	//�����ͺ��̽� 
	
	DBConnection dbConnection = new DBConnection();
	
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    private JTextField passwordField;
    private JTextField nameField;
    private JTextField phonenumberField;
    private JTextField emailField;
    
    public SignUp_Page() {
        setTitle("SignUp Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        Container signUp = getContentPane();

        Color back_color = new Color(255, 255, 255);
        signUp.setBackground(back_color);

        signUp.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 50, 0);

        JLabel signUpText = new JLabel("Sign Up");
        signUpText.setForeground(new Color(124, 124, 124));
        signUpText.setFont(new Font("������������� Bold", Font.BOLD, 30));

        signUp.add(signUpText, constraints);

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
        addField(inputPanel, "Name", "  �̸��� �Է��ϼ���", 17, labelConstraints, fieldConstraints);
        addField(inputPanel, "Phone Number", "  ��ȭ��ȣ�� �Է��ϼ���", 17, labelConstraints, fieldConstraints);
        addField(inputPanel, "Email", "  �̸����� �Է��ϼ���", 17, labelConstraints, fieldConstraints);

        constraints.gridy = 2;
        signUp.add(inputPanel, constraints);
        
        JButton signBtn = new JButton("�����ϱ�");
        signBtn.setForeground(Color.WHITE);
        signBtn.setBackground(new Color(88, 172, 255)); // ��ư ���� ����
        signBtn.setFont(new Font("������������� Bold", Font.BOLD, 13));
        signBtn.setFocusPainted(false);
        signBtn.setBorder(null);
        
        Dimension signBtn_buttonSize = new Dimension(80, 30);
        signBtn.setPreferredSize(signBtn_buttonSize);
        
        constraints.gridy = 3;
        signUp.add(signBtn, constraints);
        
        signBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String id = idField.getText();
            	String password = passwordField.getText();
            	String name = nameField.getText();
            	String phonenumber = phonenumberField.getText();
            	String email = emailField.getText();
            	
            	boolean isSuccess = dbConnection.insertUser(id, password, name, phonenumber, email);
            	
            	if(isSuccess) {
            		System.out.println("ȸ������ ����");
            	}
            	else {
            		
            	}
                Start_Page startPage = new Start_Page();
                startPage.setVisible(true);
                dispose(); // ���� ������ �ݱ�
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
        } else if (labelText.equals("Name")) {
            nameField = field;
        } else if (labelText.equals("Phone Number")) {
            phonenumberField = field;
        } else if (labelText.equals("Email")) {
            emailField = field;
        } 
    }
    
}

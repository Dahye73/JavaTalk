package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

import database.*;

public class Profile_Page extends JFrame {
	
	private DBConnection dbConnection;
	
    public Profile_Page() {
    	
        setTitle("Profile Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);

        getContentPane().setBackground(Color.WHITE); // ������ ȭ��Ʈ�� ����
        setLayout(null);
        
        JLabel pageTitle = new JLabel("JavaTalk");
        pageTitle.setForeground(new Color(64, 64, 64));
        pageTitle.setFont(new Font("Intel", Font.PLAIN, 15));
        pageTitle.setBounds(15, 0, 200, 50);
        add(pageTitle);
        
        JButton searchButton = new JButton("ģ���˻�");
        searchButton.setForeground(Color.black);
        searchButton.setBackground(Color.white);
        searchButton.setFont(new Font("��������ڵ�", Font.PLAIN, 13));
        searchButton.setFocusPainted(false);
        searchButton.setBounds(380, 10, 100, 50);
        
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	showUserNames();
            }
        });
        
        dbConnection = new DBConnection();
        
        
        add(searchButton);
        
        JLabel profile_Text = new JLabel("�� ������");
        profile_Text.setForeground(new Color(64, 64, 64));
        profile_Text.setFont(new Font("��������ڵ�", Font.PLAIN, 13));
        profile_Text.setBounds(20, 60, 200, 30);
        add(profile_Text);
        
        ImageIcon Myprofile_Image = new ImageIcon(Profile_Page.class.getResource("/image/person10.png"));
        UserProfileButton Myprofilebtn = new UserProfileButton(Myprofile_Image);
        Myprofilebtn.setText(Login_Page.username);
        Myprofilebtn.setBounds(20, 100, 450, 80);
        add(Myprofilebtn);
        
        JLabel Friend_Text = new JLabel("ģ��");
        Friend_Text.setForeground(new Color(64, 64, 64));
        Friend_Text.setFont(new Font("��������ڵ�", Font.PLAIN, 13));
        Friend_Text.setBounds(20, 200, 200, 30);
        add(Friend_Text);
        
        FriendList friendList = new FriendList(Login_Page.userid);

        JScrollPane scrollPane = new JScrollPane(friendList);
        scrollPane.setBounds(20, 240, 450, 350);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);

        // �ϴܹ� ����
        
        BottomBar bottombar = new BottomBar();
        add(bottombar);

        setLocationRelativeTo(null); 
        setVisible(true);
        
    }
    
    private void showUserNames() {
        String[] userNames = dbConnection.getUserNames();

        if (userNames != null) {
            
        	JDialog dialog = new JDialog(this, "����� �˻�", true);
        	
        	JPanel panel = new JPanel();
        	panel.setLayout(new GridBagLayout());
        	panel.setBackground(Color.white);
        	
        	GridBagConstraints constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.insets = new Insets(5, 5, 5, 5);
        	
        	for (String userName : userNames) {
        		
        		JPanel userPanel = new JPanel();
        		userPanel.setBackground(Color.white);
        		
        		JLabel nameLabel = new JLabel(userName);
        		JButton addButton = new JButton("�߰�");
        		
        		addButton.addActionListener(new ActionListener() {
                	
                    public void actionPerformed(ActionEvent e) {
                        String friendName = nameLabel.getText();
                        String userId = Login_Page.userid;
                        
                        boolean success = dbConnection.insertFriend(friendName, userId);
                        
                        if(success) {
                        	 JOptionPane.showMessageDialog(Profile_Page.this, "ģ�� �߰��� �Ϸ�Ǿ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                        	 
                        }else {
                        	JOptionPane.showMessageDialog(Profile_Page.this, "�̹� ģ���߰� �Ͽ����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                        	
                        }
                    }
                });
        		
        		addButton.setForeground(Color.black);
        		addButton.setBackground(Color.white);
        		addButton.setFont(new Font("��������ڵ�", Font.PLAIN, 10));
        		addButton.setFocusPainted(false);
        		
        		addButton.addActionListener(new ActionListener() {
        			
        			public void actionPerformed(ActionEvent e) {
        				
        				
        			}
        		});
        		
        		userPanel.add(nameLabel);
        		userPanel.add(Box.createHorizontalStrut(0)); 
        		userPanel.add(addButton);
        		
        		constraints.gridx = 0;
                constraints.gridy++;
                constraints.insets = new Insets(10, 10, 0, 0);
                panel.add(userPanel, constraints);
                
        	}
        	
        	JTextArea textArea = new JTextArea();
        	textArea.setEditable(false);
        	
        	JScrollPane scrollPane = new JScrollPane(panel);
        	scrollPane.setBorder(null);
        	scrollPane.setPreferredSize(new Dimension(300, 200));
        	
        	JPanel searchPanel = new JPanel();
            searchPanel.setBackground(Color.WHITE);
            
            JTextField searchField = new JTextField();
            searchField.setPreferredSize(new Dimension(200, 30));
            
            JButton searchButton = new JButton("�˻�");
            
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchText = searchField.getText();
                    panel.removeAll(); // ���� ����� �г� ����
                    
                    GridBagConstraints searchConstraints = new GridBagConstraints();
                    searchConstraints.fill = GridBagConstraints.HORIZONTAL;
                    searchConstraints.insets = new Insets(5, 5, 5, 5);
                    
                    for (String userName : userNames) {
                        if (userName.equals(searchText)) {
                            JPanel userPanel = new JPanel();
                            userPanel.setBackground(Color.white);
                            
                            JLabel nameLabel = new JLabel(userName);
                            JButton addButton = new JButton("�߰�");
                            addButton.setForeground(Color.black);
                            addButton.setBackground(Color.white);
                            addButton.setFont(new Font("��������ڵ�", Font.PLAIN, 10));
                            addButton.setFocusPainted(false);
                            
                            addButton.addActionListener(new ActionListener() {
                            	
                                public void actionPerformed(ActionEvent e) {
                                    String friendName = nameLabel.getText();
                                    String userId = Login_Page.userid;
                                    
                                    boolean success = dbConnection.insertFriend(friendName, userId);
                                    
                                    if(success) {
                                    	 JOptionPane.showMessageDialog(Profile_Page.this, "ģ�� �߰��� �Ϸ�Ǿ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                                    	 
                                    }else {
                                    	JOptionPane.showMessageDialog(Profile_Page.this, "�̹� ģ�� �߰� �Ͽ����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                                    	
                                    }
                                }
                            });
                            
                            userPanel.add(nameLabel);
                            userPanel.add(Box.createHorizontalStrut(50)); 
                            userPanel.add(addButton);
                            
                            searchConstraints.gridx = 0;
                            searchConstraints.gridy++;
                            searchConstraints.insets = new Insets(10, 10, 0, 0);
                            panel.add(userPanel, searchConstraints);
                        }
                    }
                    
                    panel.revalidate(); // �г� ������Ʈ
                }
            });

            
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBackground(Color.WHITE);
            contentPanel.add(searchPanel, BorderLayout.NORTH);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            contentPanel.add(textArea, BorderLayout.SOUTH);
            
            dialog.getContentPane().add(contentPanel);

            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "����� �̸��� �������� �� �����߽��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
//    public static void main(String[] args) {
//    	new Profile_Page();
//    }
}
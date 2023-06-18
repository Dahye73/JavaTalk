package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Server.*;
import database.DBConnection;

public class ChattingList_Page extends JFrame{
	
	private DBConnection dbConnection = new DBConnection();
	private JPanel chatRoomPanel;
	
    public ChattingList_Page() {
        
    	setTitle("Chatting Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        
        Container setting = getContentPane();
        setting.setLayout(null); // GridBagLayout ����

        Color back_color = new Color(255, 255, 255);
        setting.setBackground(back_color);
        
        JLabel chatting_text = new JLabel("ä��");
        chatting_text.setForeground(new Color(64, 64, 64));
        chatting_text.setFont(new Font("Intel", Font.PLAIN, 20));
        chatting_text.setBounds(20, 0, 200, 50);
        add(chatting_text);
 
        BottomBar bottombar = new BottomBar();
        
        setting.add(bottombar);
        setLocationRelativeTo(null); 
        setVisible(true);
        
        JButton addRoomBtn = new JButton("ä�ù� ����");
        addRoomBtn.setForeground(Color.black);
        addRoomBtn.setBackground(Color.white);
        addRoomBtn.setFont(new Font("��������ڵ�", Font.PLAIN, 13));
        addRoomBtn.setFocusPainted(false);
        addRoomBtn.setBounds(370, 10, 110, 40);
        
        chatRoomPanel = new JPanel();
        chatRoomPanel.setLayout(new BoxLayout(chatRoomPanel, BoxLayout.Y_AXIS));
        chatRoomPanel.setBackground(new Color(255, 239, 249));
        
        
        JScrollPane scrollPane = new JScrollPane(chatRoomPanel);
        scrollPane.setBounds(10, 60, 470, 550);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ���� ��ũ�� ��Ȱ��ȭ
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // ���� ��ũ�� Ȱ��ȭ
        setting.add(scrollPane);
        
        refreshChatRooms();
        
        addRoomBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
                String[] friendNames = dbConnection.getFriendNames(Login_Page.userid);
                
                JDialog dialog = new JDialog((JFrame)null, "ä�ù� ����", true);

                // �г� ����
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(new EmptyBorder(10, 10, 10, 10));
                
                panel.setBackground(Color.WHITE);
                
                // ��� �г�
                JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                topPanel.setBackground(Color.white);
                JLabel nameLabel = new JLabel("ä�ù� �̸�:");
                JTextField nameField = new JTextField(20);
                JLabel friendLabel = new JLabel("��ȭ ��� ����:");
                topPanel.add(nameLabel);
                topPanel.add(nameField);
                topPanel.add(friendLabel);

                // �߾� �г�
                JPanel friendPanel = new JPanel(new GridLayout(friendNames.length, 1, 5, 5));
                friendPanel.setBackground(Color.white);
                List<JCheckBox> checkBoxList = new ArrayList<>();
                for (String friendName : friendNames) {
                    JCheckBox checkBox = new JCheckBox(friendName);
                    checkBox.setBackground(Color.white);
                    friendPanel.add(checkBox);
                    checkBoxList.add(checkBox);
                }
                
                JScrollPane friendScrollPane = new JScrollPane(friendPanel);
                friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // ���� ��ũ�� �׻� ǥ��
                
                
                // �߾� �гο� ��ũ�� �г� �߰�
                JPanel centerPanel = new JPanel(new BorderLayout());
                centerPanel.setBackground(Color.white);
                centerPanel.add(friendScrollPane, BorderLayout.CENTER);
                // �ϴ� �г�
                JPanel bottomPanel = new JPanel();
                bottomPanel.setBackground(Color.white);
                JButton createButton = new JButton("����");
                createButton.setBackground(Color.pink);
                createButton.setForeground(Color.white);
                createButton.setBorderPainted(false);
                
                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                    	String chatRoomName = nameField.getText();
                        List<String> selectedFriends = new ArrayList<>();
                        
                        for(JCheckBox checkBox : checkBoxList) {
                        	if(checkBox.isSelected()) {
                        		selectedFriends.add(checkBox.getText());
                        	}
                        }
                    	
                    	// ���õ� ģ�� ����� �������� ���� �߰�
                        // ���õ� ä�ù� �̸��� ģ�� ����� ����Ͽ� ä�ù��� �����ϴ� ���� �߰�
                        int roomId = dbConnection.addChatRoom(chatRoomName);
                    	
                        dbConnection.addChatRoomMember(roomId, Login_Page.userid);
                        for(String friend : selectedFriends) {
                        	
                        	String friendId = dbConnection.getUserIdByName(friend); 
                        	dbConnection.addChatRoomMember(roomId, friendId);
                        }
                        dialog.dispose();
                    }
                });
                bottomPanel.add(createButton);

                // �гο� ������Ʈ �߰�
                panel.add(topPanel, BorderLayout.NORTH);
                panel.add(centerPanel, BorderLayout.CENTER);
                panel.add(bottomPanel, BorderLayout.SOUTH);

                // ��ȭ ����(Dialog)�� �г� �߰�
                dialog.getContentPane().add(panel);

                // ��ȭ ����(Dialog) ũ�� �� ��ġ ����
                dialog.setSize(300, 200);
                dialog.setLocationRelativeTo(null); // ȭ�� �߾ӿ� ǥ��
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // ��ȭ ����(Dialog) ǥ��
                dialog.setVisible(true);
                
         
        	}
        });
        setting.add(addRoomBtn);
	}

    private void refreshChatRooms() {
        chatRoomPanel.removeAll();

        String[] chatRooms = dbConnection.getChatRooms(Login_Page.userid);
        
        // ù ��° �ڽ� ���ʿ� ���� �߰�
        chatRoomPanel.add(Box.createVerticalStrut(10));

        for (String roomName : chatRooms) {
            JPanel roomPanel = new JPanel(new BorderLayout());
            roomPanel.setBorder(new LineBorder(new Color(233, 223, 223), 1)); // ��輱 ����
            roomPanel.setBackground(Color.WHITE);
            roomPanel.setPreferredSize(new Dimension(450, 60)); // �г��� ũ�� ����
            roomPanel.setMaximumSize(new Dimension(450, 60)); // �г��� �ִ� ũ�� ����
            roomPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // ���� ����
            
            JButton roomButton = new JButton(roomName);
            roomButton.setBackground(Color.WHITE);
            roomButton.setFocusPainted(false);
            roomButton.setHorizontalAlignment(JButton.LEFT); // ���� ����
            roomButton.setBorder(new EmptyBorder(10, 10, 10, 10));// ���� ����
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedRoom = roomButton.getText();
                    new Chatting_Page(selectedRoom, "localhost", ServerLauncher.port);
                }
            });
            
            roomPanel.add(roomButton, BorderLayout.CENTER);
            
            chatRoomPanel.add(roomPanel);
            
            // �� �ڽ� ���̿� 10�ȼ��� ���� �߰�
            chatRoomPanel.add(Box.createVerticalStrut(10));
        }

        chatRoomPanel.revalidate();
        chatRoomPanel.repaint();
    }



	
//	public static void main(String[] args) {
//		new ChattingList_Page();
//	}
}
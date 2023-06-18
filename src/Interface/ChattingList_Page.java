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
        setting.setLayout(null); // GridBagLayout 설정

        Color back_color = new Color(255, 255, 255);
        setting.setBackground(back_color);
        
        JLabel chatting_text = new JLabel("채팅");
        chatting_text.setForeground(new Color(64, 64, 64));
        chatting_text.setFont(new Font("Intel", Font.PLAIN, 20));
        chatting_text.setBounds(20, 0, 200, 50);
        add(chatting_text);
 
        BottomBar bottombar = new BottomBar();
        
        setting.add(bottombar);
        setLocationRelativeTo(null); 
        setVisible(true);
        
        JButton addRoomBtn = new JButton("채팅방 생성");
        addRoomBtn.setForeground(Color.black);
        addRoomBtn.setBackground(Color.white);
        addRoomBtn.setFont(new Font("나눔고딕코딩", Font.PLAIN, 13));
        addRoomBtn.setFocusPainted(false);
        addRoomBtn.setBounds(370, 10, 110, 40);
        
        chatRoomPanel = new JPanel();
        chatRoomPanel.setLayout(new BoxLayout(chatRoomPanel, BoxLayout.Y_AXIS));
        chatRoomPanel.setBackground(new Color(255, 239, 249));
        
        
        JScrollPane scrollPane = new JScrollPane(chatRoomPanel);
        scrollPane.setBounds(10, 60, 470, 550);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 세로 스크롤 활성화
        setting.add(scrollPane);
        
        refreshChatRooms();
        
        addRoomBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
                String[] friendNames = dbConnection.getFriendNames(Login_Page.userid);
                
                JDialog dialog = new JDialog((JFrame)null, "채팅방 생성", true);

                // 패널 생성
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(new EmptyBorder(10, 10, 10, 10));
                
                panel.setBackground(Color.WHITE);
                
                // 상단 패널
                JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                topPanel.setBackground(Color.white);
                JLabel nameLabel = new JLabel("채팅방 이름:");
                JTextField nameField = new JTextField(20);
                JLabel friendLabel = new JLabel("대화 상대 선택:");
                topPanel.add(nameLabel);
                topPanel.add(nameField);
                topPanel.add(friendLabel);

                // 중앙 패널
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
                friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 세로 스크롤 항상 표시
                
                
                // 중앙 패널에 스크롤 패널 추가
                JPanel centerPanel = new JPanel(new BorderLayout());
                centerPanel.setBackground(Color.white);
                centerPanel.add(friendScrollPane, BorderLayout.CENTER);
                // 하단 패널
                JPanel bottomPanel = new JPanel();
                bottomPanel.setBackground(Color.white);
                JButton createButton = new JButton("생성");
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
                    	
                    	// 선택된 친구 목록을 가져오는 로직 추가
                        // 선택된 채팅방 이름과 친구 목록을 사용하여 채팅방을 생성하는 로직 추가
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

                // 패널에 컴포넌트 추가
                panel.add(topPanel, BorderLayout.NORTH);
                panel.add(centerPanel, BorderLayout.CENTER);
                panel.add(bottomPanel, BorderLayout.SOUTH);

                // 대화 상자(Dialog)에 패널 추가
                dialog.getContentPane().add(panel);

                // 대화 상자(Dialog) 크기 및 위치 설정
                dialog.setSize(300, 200);
                dialog.setLocationRelativeTo(null); // 화면 중앙에 표시
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // 대화 상자(Dialog) 표시
                dialog.setVisible(true);
                
         
        	}
        });
        setting.add(addRoomBtn);
	}

    private void refreshChatRooms() {
        chatRoomPanel.removeAll();

        String[] chatRooms = dbConnection.getChatRooms(Login_Page.userid);
        
        // 첫 번째 박스 위쪽에 여백 추가
        chatRoomPanel.add(Box.createVerticalStrut(10));

        for (String roomName : chatRooms) {
            JPanel roomPanel = new JPanel(new BorderLayout());
            roomPanel.setBorder(new LineBorder(new Color(233, 223, 223), 1)); // 경계선 설정
            roomPanel.setBackground(Color.WHITE);
            roomPanel.setPreferredSize(new Dimension(450, 60)); // 패널의 크기 설정
            roomPanel.setMaximumSize(new Dimension(450, 60)); // 패널의 최대 크기 설정
            roomPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 왼쪽 정렬
            
            JButton roomButton = new JButton(roomName);
            roomButton.setBackground(Color.WHITE);
            roomButton.setFocusPainted(false);
            roomButton.setHorizontalAlignment(JButton.LEFT); // 왼쪽 정렬
            roomButton.setBorder(new EmptyBorder(10, 10, 10, 10));// 여백 설정
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedRoom = roomButton.getText();
                    new Chatting_Page(selectedRoom, "localhost", ServerLauncher.port);
                }
            });
            
            roomPanel.add(roomButton, BorderLayout.CENTER);
            
            chatRoomPanel.add(roomPanel);
            
            // 각 박스 사이에 10픽셀의 여백 추가
            chatRoomPanel.add(Box.createVerticalStrut(10));
        }

        chatRoomPanel.revalidate();
        chatRoomPanel.repaint();
    }



	
//	public static void main(String[] args) {
//		new ChattingList_Page();
//	}
}
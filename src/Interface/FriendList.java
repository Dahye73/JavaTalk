package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import database.DBConnection;

public class FriendList extends JPanel {
    private JPanel friendPanel; 
    private DBConnection dbConnection = new DBConnection();
    private String userId;
    
    public FriendList(String userId) {
        this.userId = userId;
        setBackground(Color.WHITE);
        
        friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout
        friendPanel.setBackground(Color.WHITE);
        add(friendPanel);
        
        updateFriendList();
    }
    
    public void updateFriendList() {
        String[] friendNames = dbConnection.getFriendNames(userId);
        friendPanel.removeAll(); 

        if (friendNames != null && friendNames.length > 0) {
            for (String friendName : friendNames) {
                ImageIcon friendImage = new ImageIcon(Profile_Page.class.getResource("/image/person10.png"));
                UserProfileButton friendBtn = new UserProfileButton(friendImage);
                friendBtn.setText(friendName);
                friendBtn.setPreferredSize(new Dimension(450, 80));
                friendPanel.add(friendBtn);
            }
        } else {
            JLabel noFriendLabel = new JLabel("친구가 없습니다.");
            noFriendLabel.setHorizontalAlignment(SwingConstants.CENTER);
            friendPanel.add(noFriendLabel);
        }

        revalidate(); 
        repaint();
    }
}

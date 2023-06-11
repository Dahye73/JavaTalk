package Interface;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FriendList extends JPanel{
	
	public FriendList(ImageIcon friendImage, String friendName) {
        setLayout(null);
        setBackground(Color.WHITE);

        UserProfileButton friendBtn = new UserProfileButton(friendImage);
        friendBtn.setText(friendName);
        friendBtn.setBounds(0, 0, 450, 80);
        add(friendBtn);
    }
}

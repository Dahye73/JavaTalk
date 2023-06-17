package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BottomBar extends JPanel {
	
    public BottomBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(255, 255, 255));

        JLabel friendLabel = new JLabel("Ä£±¸");
        friendLabel.setForeground(new Color(64, 64, 64));
        friendLabel.setFont(new Font("³ª´®°íµñÄÚµù", Font.PLAIN, 17));
        friendLabel.setBorder(new EmptyBorder(10, 70, 10, 120));
        
        friendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Profile_Page profilePage = PageManager.getInstance().getProfilePage();
                profilePage.setVisible(true);

            }
        });
        add(friendLabel);

        JLabel chatLabel = new JLabel("Ã¤ÆÃ");
        chatLabel.setForeground(new Color(64, 64, 64));
        chatLabel.setFont(new Font("³ª´®°íµñÄÚµù", Font.PLAIN, 17));
        chatLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        chatLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChattingList_Page chattingListPage = PageManager.getInstance().getChattingListPage();
                chattingListPage.setVisible(true);

            }
        });
        
        add(chatLabel);
        
        
        JLabel settingsLabel = new JLabel("¼³Á¤");
        settingsLabel.setForeground(new Color(64, 64, 64));
        settingsLabel.setFont(new Font("³ª´®°íµñÄÚµù", Font.PLAIN, 17));
        settingsLabel.setBorder(new EmptyBorder(10, 120, 10, 70));
        
        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Setting_Page settingPage = PageManager.getInstance().getSettingPage();
                settingPage.setVisible(true);
            
            }
        });
        add(settingsLabel);

        setBounds(0, 610, 500, 60);
    }
}


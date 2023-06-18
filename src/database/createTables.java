package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class createTables {
    private Connection con;
    private Statement st;

    public createTables(Connection con, Statement st) {
        this.con = con;
        this.st = st;
    }

    public void createTablesIfNotExists() {
        try {
            // users ���̺� ����
        	String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                    + "id VARCHAR(20) PRIMARY KEY,"
                    + "password VARCHAR(20),"
                    + "name VARCHAR(20),"
                    + "phonenumber VARCHAR(20),"
                    + "email VARCHAR(50)"
                    + ")";
                st.executeUpdate(createUsersTableQuery);

                // friend ���̺� ����
                String createFriendTableQuery = "CREATE TABLE IF NOT EXISTS friend ("
                    + "user_id VARCHAR(50),"
                    + "friend_status TINYINT,"
                    + "friend_name VARCHAR(255),"
                    + "FOREIGN KEY (user_id) REFERENCES users(id)"
                    + ")";
                st.executeUpdate(createFriendTableQuery);

            // chatroom ���̺� ����
            String createChatRoomTableQuery = "CREATE TABLE IF NOT EXISTS chatroom ("
                + "room_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "room_name VARCHAR(255)"
                + ")";
            st.executeUpdate(createChatRoomTableQuery);

            // chatroommember ���̺� ����
            String createChatRoomMemberTableQuery = "CREATE TABLE IF NOT EXISTS chatroommember ("
                + "room_id INT,"
                + "user_id VARCHAR(20),"
                + "FOREIGN KEY (room_id) REFERENCES chatroom(room_id),"
                + "FOREIGN KEY (user_id) REFERENCES users(id)"
                + ")";
            st.executeUpdate(createChatRoomMemberTableQuery);

            // message ���̺� ����
            String createMessageTableQuery = "CREATE TABLE IF NOT EXISTS message ("
                + "message_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "room_id INT,"
                + "user_id VARCHAR(20),"
                + "text TEXT,"
                + "timestamp TIMESTAMP,"
                + "FOREIGN KEY (room_id) REFERENCES chatroom(room_id),"
                + "FOREIGN KEY (user_id) REFERENCES users(id)"
                + ")";
            st.executeUpdate(createMessageTableQuery);

            System.out.println("���̺� ������ �Ϸ�Ǿ����ϴ�.");
        } catch (SQLException e) {
            System.out.println("���̺� ���� ����: " + e.getMessage());
        }
    }
}

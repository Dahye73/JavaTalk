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
            // users 테이블 생성
        	String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                    + "id VARCHAR(20) PRIMARY KEY,"
                    + "password VARCHAR(20),"
                    + "name VARCHAR(20),"
                    + "phonenumber VARCHAR(20),"
                    + "email VARCHAR(50)"
                    + ")";
                st.executeUpdate(createUsersTableQuery);

                // friend 테이블 생성
                String createFriendTableQuery = "CREATE TABLE IF NOT EXISTS friend ("
                    + "user_id VARCHAR(50),"
                    + "friend_status TINYINT,"
                    + "friend_name VARCHAR(255),"
                    + "FOREIGN KEY (user_id) REFERENCES users(id)"
                    + ")";
                st.executeUpdate(createFriendTableQuery);

            // chatroom 테이블 생성
            String createChatRoomTableQuery = "CREATE TABLE IF NOT EXISTS chatroom ("
                + "room_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "room_name VARCHAR(255)"
                + ")";
            st.executeUpdate(createChatRoomTableQuery);

            // chatroommember 테이블 생성
            String createChatRoomMemberTableQuery = "CREATE TABLE IF NOT EXISTS chatroommember ("
                + "room_id INT,"
                + "user_id VARCHAR(20),"
                + "FOREIGN KEY (room_id) REFERENCES chatroom(room_id),"
                + "FOREIGN KEY (user_id) REFERENCES users(id)"
                + ")";
            st.executeUpdate(createChatRoomMemberTableQuery);

            // message 테이블 생성
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

            System.out.println("테이블 생성이 완료되었습니다.");
        } catch (SQLException e) {
            System.out.println("테이블 생성 오류: " + e.getMessage());
        }
    }
}

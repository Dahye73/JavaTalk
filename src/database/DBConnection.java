package database;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.FileInputStream;
import java.util.Properties;

import Interface.ChatMessage;

public class DBConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnection() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);
            fis.close();

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            
            createTables createTablesObj = new createTables(con, st);
            // ���̺� ����
            createTablesObj.createTablesIfNotExists();
            
        } catch (Exception e) {
            System.out.println("�����ͺ��̽� ���� ����: " + e.getMessage());
        }
    }


    public boolean isAdmin(String adminID, String adminPassword) {
        try {
            String SQL = "select * from admin where adminID = '" + adminID + "' and adminPassword = '" + adminPassword + "'";
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }
        return false;
    }
    
    //ȸ������ 
    public boolean insertUser(String id, String password, String name, String phonenumber, String email) {
    	
    	try { 
    		String SQL = "insert into users(id, password, name, phonenumber, email) values (?, ?, ?, ?, ?)";
    		
    		PreparedStatement preparedStatement = con.prepareStatement(SQL);
    		preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phonenumber);
            preparedStatement.setString(5, email);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if(rowsAffected > 0) {
            	return true;
            }
    	}catch (Exception e) {
    		System.out.println("�����ͺ��̽� ���� ����: " + e.getMessage());
    	}
    	
    	return false;
    }
    
    //���̵� �ߺ�üũ
    public boolean isExistingUser(String id) {
        try {
            String SQL = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                return true; // �̹� ��� ���� ���̵� ������
            }
        } catch (Exception e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }
        
        return false; // ��� ������ ���̵�
    }

    //�α���
    public boolean authenticateUser(String id, String password) {
    	
    	try {
    		String SQL = "select * from users where id = ? and password = ?";
    		
    		PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
            	return true;
            }
    	}catch (Exception e) {
    		System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
    	}
    	
    	return false;
    }
    
    //ȸ�� ���� ��������
    
    public String getUser(String id, String data) {
        try {
            String SQL = "select " + data + " from users where id = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                return rs.getString(data);
            }
        } catch (Exception e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }
        
        return null;
    }
    
    // ����ڵ��� �̸� ��������
    public String[] getUserNames() {
    	
    	try {
    		
    		String SQL = "SELECT name FROM users";
    		PreparedStatement preparedStatement = con.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            
            StringBuilder names = new StringBuilder();
            
            while(rs.next()) {
            	
            	String userName = rs.getString("name");
            	names.append(userName).append("\n");
            }
            
            String[] nameArray = names.toString().split("\n");
            return nameArray;
    		
    	}catch (Exception e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }

        return null;
    }
   
    //ģ�� �߰��ϱ�
    public boolean insertFriend(String friendName, String userId) {
        try {
            // Check if friend already exists with friend_status = 1
            String CHECK_SQL = "SELECT COUNT(*) FROM Friend WHERE friend_name = ? AND user_id = ? AND friend_status = 1";
            PreparedStatement checkStatement = con.prepareStatement(CHECK_SQL);
            checkStatement.setString(1, friendName);
            checkStatement.setString(2, userId);
            ResultSet rs = checkStatement.executeQuery();
            if(rs.next() && rs.getInt(1) > 0) {
                // Friend already exists with friend_status = 1
                return false;
            }
            
            // If friend doesn't exist with friend_status = 1, insert into database
            String INSERT_SQL = "INSERT INTO Friend (friend_name, user_id, friend_status) VALUES (?, ?, 1)";
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, friendName);
            preparedStatement.setString(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("�����ͺ��̽� ���� ����: " + e.getMessage());
        }

        return false;
    }


    
    //���� ��� ��������
    public String[] getFriendNames(String userId) {
        String[] friendNames = null;
        try {
            String sql = "SELECT friend_name FROM Friend WHERE user_id = ? AND friend_status = 1";
            PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            friendNames = new String[rowCount];

            int index = 0;

            while (resultSet.next()) {
                String friendName = resultSet.getString("friend_name");
                friendNames[index] = friendName;
                index++;
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friendNames;
    }
    
    public int addChatRoom(String roomName) {
    	
    	int roomId = -1;
    	
    	try {
    		
    		String SQL = "insert into chatroom (room_name) values (?)";
    		
    		PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, roomName);
            preparedStatement.executeUpdate();
            
            //get room_id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomId = generatedKeys.getInt(1);
            }
            
    	}catch (SQLException ex) {
            System.out.println("�����ͺ��̽� ���� ����: " + ex.getMessage());
        }
    	System.out.println("����� ����");
    	return roomId;
    }
    
    public String getUserIdByName(String friendName) {
        String userId = null;

        try {
            String SQL = "SELECT id FROM users WHERE name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, friendName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getString("id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }
        return userId;
    }

    public void addChatRoomMember(int roomId, String friendId) {
        try {
            String SQL = "INSERT INTO chatroommember (room_id, user_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, friendId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("��� �߰� ����");
            } else {
                System.out.println("��� �߰� ����");
            }
        } catch (SQLException e) {
            System.out.println("�����ͺ��̽� ���� ����: " + e.getMessage());
        }
    }
    
    public String[] getChatRooms(String userId) {
        List<String> chatRooms = new ArrayList<>();
        
        try {
            // ����� id�� �ش��ϴ� room_id ã��
            String SQL1 = "SELECT room_id FROM chatroommember WHERE user_id = ?";
            PreparedStatement preparedStatement1 = con.prepareStatement(SQL1);
            preparedStatement1.setString(1, userId);
            ResultSet rs1 = preparedStatement1.executeQuery();
            
            while (rs1.next()) {
                // �� room_id�� �ش��ϴ� room_name ã��
                String SQL2 = "SELECT room_name FROM chatroom WHERE room_id = ?";
                PreparedStatement preparedStatement2 = con.prepareStatement(SQL2);
                preparedStatement2.setInt(1, rs1.getInt("room_id"));
                ResultSet rs2 = preparedStatement2.executeQuery();
                
                if (rs2.next()) {
                    chatRooms.add(rs2.getString("room_name"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("�����ͺ��̽� ���� ����: " + ex.getMessage());
        }
        return chatRooms.toArray(new String[0]);
    }
    
    public int getRoomId(String roomName) {
        int roomId = -1;

        try {
            String SQL = "SELECT room_id FROM chatroom WHERE room_name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, roomName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roomId = resultSet.getInt("room_id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("�����ͺ��̽� �˻� ����: " + e.getMessage());
        }

        return roomId;
    }
    
    public void sendMessageToChatRoom(int roomId, String userId, String message, Timestamp timestamp) {
        try {
            String SQL = "INSERT INTO message (room_id, user_id, text, timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, message);
            preparedStatement.setTimestamp(4, timestamp);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("�޽��� ���� ����");
            } else {
                System.out.println("�޽��� ���� ����");
            }
        } catch (SQLException e) {
            System.out.println("�����ͺ��̽� ���� ����: " + e.getMessage());
        }
    }
    
    public List<String> getUserNamesByRoomId(int roomId, String userId) {
        
        List<String> userNames = new ArrayList<>();

        String query = "SELECT u.name FROM users u " +
                       "JOIN chatroommember c ON u.id = c.user_id " +
                       "WHERE c.room_id = ? AND u.id != ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String userName = rs.getString(1); // Changed getInt to getString
                    userNames.add(userName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userNames;
    }
    
    public List<ChatMessage> getChatHistory(int roomId, String userId) {
        List<ChatMessage> chatHistory = new ArrayList<>();
        String query = "SELECT user_id, text, timestamp FROM message WHERE room_id = ? ORDER BY timestamp";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String messageUserId = rs.getString("user_id");
                String message = rs.getString("text");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                ChatMessage chatMessage = new ChatMessage(messageUserId, message, timestamp);
                chatHistory.add(chatMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistory;
    }
}
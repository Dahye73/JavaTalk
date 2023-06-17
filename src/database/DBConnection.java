package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //JDBC 드라이버 로딩
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatalk", "shin2", "shin2");
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 오류: " + e.getMessage());
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
            System.out.println("데이터베이스 검색 오류: " + e.getMessage());
        }
        return false;
    }
    
    //회원가입 
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
    		System.out.println("데이터베이스 삽입 오류: " + e.getMessage());
    	}
    	
    	return false;
    }
    
    //아이디 중복체크
    public boolean isExistingUser(String id) {
        try {
            String SQL = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                return true; // 이미 사용 중인 아이디가 존재함
            }
        } catch (Exception e) {
            System.out.println("데이터베이스 검색 오류: " + e.getMessage());
        }
        
        return false; // 사용 가능한 아이디
    }

    //로그인
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
    		System.out.println("데이터베이스 검색 오류: " + e.getMessage());
    	}
    	
    	return false;
    }
    
    //회원 정보 가져오기
    
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
            System.out.println("데이터베이스 검색 오류: " + e.getMessage());
        }
        
        return null;
    }
    
    // 사용자들의 이름 가져오기
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
            System.out.println("데이터베이스 검색 오류: " + e.getMessage());
        }

        return null;
    }
   
    //친구 추가하기
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
            System.out.println("데이터베이스 삽입 오류: " + e.getMessage());
        }

        return false;
    }


    
    //찬구 목록 가져오기
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
            System.out.println("데이터베이스 삽입 오류: " + ex.getMessage());
        }
    	System.out.println("방생성 성공");
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
            System.out.println("데이터베이스 검색 오류: " + e.getMessage());
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
                System.out.println("멤버 추가 성공");
            } else {
                System.out.println("멤버 추가 실패");
            }
        } catch (SQLException e) {
            System.out.println("데이터베이스 삽입 오류: " + e.getMessage());
        }
    }
    
    public String[] getChatRooms(String userId) {
        List<String> chatRooms = new ArrayList<>();
        
        try {
            // 사용자 id에 해당하는 room_id 찾기
            String SQL1 = "SELECT room_id FROM chatroommember WHERE user_id = ?";
            PreparedStatement preparedStatement1 = con.prepareStatement(SQL1);
            preparedStatement1.setString(1, userId);
            ResultSet rs1 = preparedStatement1.executeQuery();
            
            while (rs1.next()) {
                // 각 room_id에 해당하는 room_name 찾기
                String SQL2 = "SELECT room_name FROM chatroom WHERE room_id = ?";
                PreparedStatement preparedStatement2 = con.prepareStatement(SQL2);
                preparedStatement2.setInt(1, rs1.getInt("room_id"));
                ResultSet rs2 = preparedStatement2.executeQuery();
                
                if (rs2.next()) {
                    chatRooms.add(rs2.getString("room_name"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("데이터베이스 쿼리 오류: " + ex.getMessage());
        }
        return chatRooms.toArray(new String[0]);
    }

}

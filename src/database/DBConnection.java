package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
   
    public boolean insertFriend(String friendName, String userId) {
        try {
            String SQL = "INSERT INTO Friend (friend_name, user_id, friend_status) VALUES (?, ?, 1)";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
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
    
}

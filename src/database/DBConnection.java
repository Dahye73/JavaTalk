package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

}

package database;

public class main {

	public static void main(String[] args) {
		DBConnection connection = new DBConnection();
		System.out.println("관리자여부: " + connection.isAdmin("admin", "1234"));

	}

}

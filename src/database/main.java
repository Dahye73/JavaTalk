package database;

public class main {

	public static void main(String[] args) {
		DBConnection connection = new DBConnection();
		System.out.println("�����ڿ���: " + connection.isAdmin("admin", "1234"));

	}

}

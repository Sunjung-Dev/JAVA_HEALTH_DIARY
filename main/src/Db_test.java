import java.sql.*;
import java.time.LocalDateTime;

public class Db_test {
	public static void select(String sql, Connection con){
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			while(res.next())
			{
				String name = res.getString(1);
				String email=res.getString(2);
				String user_num = res.getString(3);
				String gender=res.getString(4);
			}
		} catch(SQLException e ) {
			System.out.println( e);
		} 
	}

	public static void insert(String sql, Connection con){
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			// if(i==1) 
			// 	System.out.println("record add success!!"); 
			// else
			// 	System.out.println("record add failed!!");
		} catch(SQLException e ) {
			System.out.println(e);
		} 
	}

	public static Connection makeConnection(){
        String url="jdbc:mysql://javaproject.c4vh4umiu0wn.ap-northeast-1.rds.amazonaws.com:3306/health";
		String id="root";
		String password="Ourproject!";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loading success!");
			con=DriverManager.getConnection(url, id, password);
			System.out.println("database connecting success!");	
		} catch(ClassNotFoundException e) {
			System.out.println("cannot find driver!");
		} catch(SQLException e) {
			System.out.println("connection failed!");
		}
		return con;

	}
    // public static void main(String[] args) {
	// 	Connection con=makeConnection();
	// 	String sql = "SELECT * FROM user;";
	// 	select(sql, con);
    // }
}

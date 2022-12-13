import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Database {
	// static Map<String, String> user_info_map = new HashMap<String, String>()
	static String name;
	static List boardData = new ArrayList<Object>();
	static String[][] boardDatas;


	public static String select(String sql, Connection con){
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			if (res.next()){
				while(res.next())
				{
					name = res.getString(1);
					String email=res.getString(2);
					String user_num = res.getString(3);
					String gender=res.getString(4);
	
				} 
				return "true";
			}
		} catch(SQLException e ) {
			System.out.println( e);
		} 
		return "";
	}

	public static List selectBoardTable(String sql, Connection con){
		PreparedStatement pstmt = null;

		try{
			pstmt = con.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			if (res.next()){
				while(res.next())
				{
					HashMap<String, String> boardMap = new HashMap<>();
					String [] data;

					Integer board_num = res.getInt(1);
					String text_title = res.getString(2);
					String text = res.getString(3);
					String datetime = res.getString(4);
					Boolean isOk = res.getBoolean(5);
					Integer user_id = res.getInt(6);
					String type = res.getString(7);
					boardMap.put("board_num", board_num.toString());
					boardMap.put("text_title", text_title);
					boardMap.put("text", text);
					boardMap.put("datetime", datetime);
					boardMap.put("isOk", isOk.toString());
					boardMap.put("user_id", user_id.toString());
					boardMap.put("type", type);
					// data = 
					boardData.add(boardMap);
				} 
			}
		} catch(SQLException e ) {
			System.out.println( e);
		} 
		return boardData;
	}

	public static void insert(String sql, Connection con){
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
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
}

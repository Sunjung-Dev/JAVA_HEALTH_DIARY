import java.sql.*;
import java.sql.Connection ;
import java.time.LocalDateTime;


public class DB {
	
	public static Connection makeConnection() {
		String url="jdbc:mysql://javaproject.c4vh4umiu0wn.ap-northeast-1.rds.amazonaws.com:3306/sys";
		String id="root";
		String password="Ourproject!";
		Connection con=null;
				
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
	

	public static void main(String[] args) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;

		try {					
			//insert example
			Timestamp timestamp=Timestamp.valueOf(LocalDateTime.now());

			String insert="INSERT INTO city (city_id, city, country_id, last_update)";
			insert+="VALUES ('601', 'Seoul', '82', ?)";  //?:���ε庯��
					
			pstmt=con.prepareStatement(insert);
			pstmt.setTimestamp(1, timestamp);
			
			int i=pstmt.executeUpdate();
			
			if(i==1) 
				System.out.println("record add success!!"); 
			else
				System.out.println("record add failed!!");
			
			//select condition example
			String selectOneRow="SELECT * FROM city where city=?";
			pstmt=con.prepareStatement(selectOneRow);
			pstmt.setString(1, "Seoul");
			
			ResultSet rsT=pstmt.executeQuery();
			while(rsT.next())
			{
				int num=rsT.getInt(1);
				String cityName=rsT.getString(2);
				Timestamp ts=rsT.getTimestamp(4);
				System.out.println(num+"\t"+cityName+"\t"+ts);
			}
			
			
			//delete example
			String delete="DELETE FROM city WHERE city_id=?";
			
			pstmt=con.prepareStatement(delete);
			pstmt.setInt(1, 601);
			
			int j=pstmt.executeUpdate();
			
			if(j==1) 
				System.out.println("record delete success!!");
			else
				System.out.println("record delete failed!!");
			
			//update example
			String update="UPDATE city SET city=? WHERE city_id=?";
			pstmt=con.prepareStatement(update);
			pstmt.setString(1, "Busan");
			pstmt.setInt(2, 1);
			
			int t=pstmt.executeUpdate();
			
			if(t==1) 
				System.out.println("record update success!!");
			else
				System.out.println("record update failed!!");
			
			//select example
			String select="SELECT * FROM city";
			pstmt=con.prepareStatement(select);
			
			//select ������ ����� ��, executeQuery() return ResultSet			
			ResultSet rs=pstmt.executeQuery();
		
			while(rs.next())
				System.out.println(rs.getString(2));
//			{
//				String city_name=rs.getString(2);
//				System.out.println(city_name);
//			}
			
			if(rs != null) try{ rs.close();} catch(SQLException e){};         
			if(rsT != null) try{ rsT.close();} catch(SQLException e){}; 
			
		} catch(SQLException e ) {
			System.out.println("connection failed! in main()");
		}

		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
		
	}

}

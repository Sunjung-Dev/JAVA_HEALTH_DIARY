import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame{
	public static JPanel Layout() {
		Connection con = Database.makeConnection();
        String sql = "SELECT * FROM user WHERE user_name=" + Main.login_completed_name;
        if (Database.select(sql, con) == "true"){
            String message = Main.login_completed_name + "님 로그인 완료되었습니다.";
            JLabel login_completed_message = new JLabel(message);
            Main.center.add(login_completed_message);
        } else {
            return Main.login();
        };
        
		return Main.center;
	}
}
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame{
    static JLabel logoutMessage = new JLabel("<html><body>로그아웃하시겠습니까?<br></body></html>");
    static JButton logouBtn = new JButton("logout");
	public static JPanel Layout() {
		Connection con = Database.makeConnection();
        System.out.println("name: "+ Main.login_completed_name);
        String sql = "SELECT * FROM user WHERE user_name='" + Main.login_completed_name+"'";

        if (Database.select(sql, con) == "true"){
            String message = Main.login_completed_name + "님 로그인 완료되었습니다.";
            JLabel login_completed_message = new JLabel(message);
            Main.center.add(login_completed_message);
            Main.center.add(logoutMessage);
            logouBtn.addActionListener(new LogoutBtnListener());
            Main.center.add(logouBtn);
        } else {
            return Main.login();
        };
        
		return Main.center;
	}

    static class LogoutBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
			if (logouBtn.equals(e.getSource())){
				Main.center.removeAll();
                Main.login_completed_name = "";
                Main.login.setText("login");
				Main.main_page(Login.Layout());
			} 
		}
    }
}
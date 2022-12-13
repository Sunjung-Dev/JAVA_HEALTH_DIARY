
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Main extends JFrame{
	static JButton register = new JButton("회원가입");
	static JButton login = new JButton("로그인");
	static JButton board = new JButton("게시판");
	static JButton mypage = new JButton("마이페이지");
	static JButton submit = new JButton("제출하기");

	static JButton reservation = new JButton("개인 운동 예약하기");

	static JPanel north = new JPanel();
	static JPanel south = new JPanel();
	static JPanel center = new JPanel();
	static JPanel menu = new JPanel();

	static JLabel name_l = new JLabel("이름 ");
	static JTextField name = new JTextField(20);
	
	static JLabel email_l = new JLabel("메일: ");
	static JTextField email = new JTextField(20);
	
	static JLabel password_l = new JLabel("비밀번호: ");
	static JPasswordField password = new JPasswordField(20);
	
	static JLabel title = new JLabel("Healthy life!", JLabel.CENTER);
	static JRadioButton gender_bt = new JRadioButton("female");
	static JRadioButton gender_bt2 = new JRadioButton("male");
	static JFrame frame= new JFrame();
	static Container c = frame.getContentPane();
	// Container c = getContentPane();

	static String login_completed_name ="";

	public static void main_page(JPanel page){
		north.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		south.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		page.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		c.setLayout(new BorderLayout(30, 20));
		menu.add(login);
		login.addActionListener(new loginBtnListener());
		board.addActionListener(new boardBtnListener());
		menu.add(board);
		menu.add(mypage);
		north.add(title);
		north.add(menu);
		c.add(north, BorderLayout.NORTH);
		c.add(page, BorderLayout.CENTER);
		c.add(south, BorderLayout.SOUTH);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	static public JPanel login(){
		center.add(name_l);
		center.add(name);
		center.add(email_l);
		center.add(email);
		center.add(password_l);
		center.add(password);
		center.add(gender_bt);
		center.add(gender_bt2);
;		center.add(submit);
		// submit.setFont(c.getFont().deriveFont(15.0f));
		submit.addActionListener(new LoginListener());
		return center;
	}

	static public JPanel login_complete(){
		JLabel login_message = new JLabel("로그인 되었습니다!");
		center.add(login_message);
		return center;
	}
	
	public Main() {
		main_page(login());
	}
	
	static class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (submit.equals(e.getSource())) {
				center.removeAll();
				login.setText("logout");
				String name_input = name.getText();
				login_completed_name = name_input;
				String email_input = email.getText();
				String password = email.getText();
				String gender = "Unknown";
				
				if (gender_bt.isSelected()){
					gender = new String("female");
				} else if (gender_bt2.isSelected()){
					gender = new String("male");
				} 
				Connection con = Database.makeConnection();
				String sql = "INSERT INTO user (user_name, user_email, gender)";
				sql = sql + "VALUES ('" + name_input + "','" + email_input + "','" + gender + "')";
				Database.insert(sql, con);
				main_page(login_complete());
			}
		}
	}

	static class boardBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (board.equals(e.getSource())){
				center.removeAll();
				main_page(Board.Layout());
			}
		}
	}

	static class loginBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (login.equals(e.getSource())){
				center.removeAll();
				main_page(Login.Layout());
			}
		}
	}
	public static void main(String[] args) {
		new Main();

	}

}
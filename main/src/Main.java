
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Main extends JFrame{
	JButton register = new JButton("회원가입");
	JButton login = new JButton("로그인");
	JButton board = new JButton("게시판");
	JButton mypage = new JButton("마이페이지");
	JButton submit = new JButton("제출하기");
	JPanel north = new JPanel();
	JPanel center = new JPanel();
	JPanel south = new JPanel();
	JPanel menu = new JPanel();
	JLabel name_l = new JLabel("이름 ");
	JTextField name = new JTextField(20);
	
	JLabel email_l = new JLabel("메일: ");
	JTextField email = new JTextField(20);
	
	JLabel password_l = new JLabel("비밀번호: ");
	JPasswordField password = new JPasswordField(20);
	
	JLabel title = new JLabel("Healthy life!", JLabel.CENTER);
	JRadioButton gender_bt = new JRadioButton("female");
	JRadioButton gender_bt2 = new JRadioButton("male");
	
	public Main() {
		north.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		south.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout(30, 20));
		submit.addActionListener(new LoginListener());
		menu.add(login);
		menu.add(board);
		menu.add(mypage);
		north.add(title);
		north.add(menu);
		center.add(name_l);
		center.add(name);
		center.add(email_l);
		center.add(email);
		center.add(password_l);
		center.add(password);
		center.add(gender_bt);
		center.add(gender_bt2);
;		south.add(submit);
		c.add(north, BorderLayout.NORTH);
		c.add(center, BorderLayout.CENTER);
		c.add(south, BorderLayout.SOUTH);
		
		submit.setFont(c.getFont().deriveFont(15.0f));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(500, 500);
		setVisible(true);
	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (submit.equals(e.getSource())) {
				login.setText("logout");
				String name_input = name.getText();
				String email_input = email.getText();
				String password = email.getText();
				String gender = "Unknown";
				
				if (gender_bt.isSelected()){
					gender = new String("female");
				} else if (gender_bt2.isSelected()){
					gender = new String("male");
				} 
				Connection con = Db_test.makeConnection();
				String sql = "INSERT INTO user (user_name, user_email, gender)";
				sql = sql + "VALUES ('" + name_input + "','" + email_input + "','" + gender + "')";
				System.out.println(sql);
				Db_test.insert(sql, con);
				// JLabel complete_login = new JLabel("로그인 완료!");
				
				// center.add(complete_login);
				// center.revalidate();
				// center.repaint();
			}
		}
	}

	class boardListener implements ActionListener {
		JPanel board_panel=new JPanel();
		public void actionPerformed(ActionEvent e){
			if (board.equals(e.getSource())){
				JCheckBox chk2 = new JCheckBox("Java",false);

			}
		}
	}
	
	public static void main(String[] args) {
		new Main();

	}

}
package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	JButton register = new JButton("회원가입");
	JButton login = new JButton("로그인");
	JButton board = new JButton("게시판");
	JButton mypage = new JButton("마이페이지");
	JButton submit = new JButton("제출하기");
	
	public Main() {
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		JPanel menu = new JPanel();
		north.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		south.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout(30, 20));
		submit.addActionListener(new LoginListener());
		menu.add(login);
		menu.add(board);
		menu.add(mypage);
		
		JLabel name_l = new JLabel("이름 ");
		JTextField name = new JTextField(20);
		
		JLabel email_l = new JLabel("메일: ");
		JTextField email = new JTextField(20);
		
		JLabel password_l = new JLabel("비밀번호: ");
		JPasswordField password = new JPasswordField(20);
		
		JLabel title = new JLabel("Healthy life!", JLabel.CENTER);
		
		
		north.add(title);
		north.add(menu);
		center.add(name_l);
		center.add(name);
		center.add(email_l);
		center.add(email);
		center.add(password_l);
		center.add(password);
		south.add(submit);
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
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();

	}

}

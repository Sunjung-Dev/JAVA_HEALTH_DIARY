
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Calendar;

public class Mypage extends JFrame{
	public Mypage() {
		setTitle("Mypage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setSize(500,700);
		c.add(new Banner(),BorderLayout.NORTH);
		c.add(new MyPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	class Banner extends Panel{
		JButton loginBtn = new JButton("로그인");
		JButton postBtn = new JButton("게시판");
		JButton mypageBtn = new JButton("마이페이지");
		
		public Banner() {
			setBackground(Color.LIGHT_GRAY);
			add(loginBtn);
			add(postBtn);
			add(mypageBtn);
		}		
	}	
	
	class MyPanel extends Panel{
		JButton mypostBtn = new JButton("나의 작성글 확인");
		JButton newpostBtn = new JButton("식단&운동 일지 작성");
		
		public MyPanel() {
			add(mypostBtn);
			add(newpostBtn);
			//setLayout(null);
			//mypostBtn.setBounds(80,550,150,40);
			//newpostBtn.setBounds(250,550,150,40);
			
			mypostBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}		
			});
			
			
			newpostBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane input = new JOptionPane();
					String data = input.showInputDialog("식단: ");
					/*
					int result = JOptionPane.showConfirmDialog(null, "계속할 것입니까?", "Confirm", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.CLOSED_OPTION)
						return;
					else if (result == JOptionPane.YES_OPTION)
						return;
					*/
				}
			});
			
		}
		
	}
	public static void main (String[] args) {
		new Mypage();
	}
}

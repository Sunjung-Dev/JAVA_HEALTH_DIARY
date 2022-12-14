

import java.awt.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.TableModel;

import org.w3c.dom.events.MouseEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.*;
import java.util.List;

public class Board extends JFrame implements MouseListener{
	static JRadioButton diet = new JRadioButton("DIET");
	static JRadioButton weight = new JRadioButton("WEIGHT"); 
	static JButton select_btn = new JButton("관련게시글 보기");
	static JTable table;
	static JTextField txtTitle = new JTextField(10);
	static JTextField txtText = new JTextField(10);
	static JTextField txtData = new JTextField(10);
	static JTextField txtUserId = new JTextField(10);
	
	static JLabel login_info = new JLabel("아직 로그인 되지 않았습니다. 로그인 먼저 시도해주세요. \n 위의 메뉴 중 로그인 메뉴를 선택해주세요!");
	static Image loginIcon = new ImageIcon(Main.class.getResource("img/login.png")).getImage();
	static JLabel loginLabel = new JLabel();
	BoardMemo board;

	public static JPanel Layout() {
		if (Main.login_completed_name == ""){
			Image image = loginIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon ximage = new ImageIcon(image);
			login_info.setBackground(Color.YELLOW);
			login_info.setOpaque(true);
			loginLabel.setIcon(ximage);
			Main.center.add(loginLabel);
			Main.center.add(login_info);
		} else {
			Main.center.setBorder(BorderFactory.createEmptyBorder(10, 100, 100, 100));
			JLabel user_info = new JLabel(Main.login_completed_name + "님 원하시는 운동 목적을 선택해주세요!");
			user_info.setBackground(Color.YELLOW);
			user_info.setOpaque(true);
			Main.center.add(user_info);
			Main.center.add(diet);
			Main.center.add(weight);
			Main.center.add(select_btn);
			select_btn.addActionListener(new SelectListner());
		}
		
		return Main.center;
	}

	public static JPanel AddWeightDiet(String type){
		Connection con = Database.makeConnection();
		String sql = "SELECT * from board WHERE type='" + type + "'";
		System.out.println(sql);
		List weightdietData = new ArrayList<Object>();
		weightdietData = Database.selectBoardTable(sql, con);
		System.out.println(weightdietData);
		Main.center.removeAll();
		String header[] = {"board_num", "text_title", "text", "datetime", "isOk", "user_id", "type"};
		String contentes[][] = {
			{"1", "hi", "sdf", "123", "df", "123", "32"},
			{"1", "jhi", "123", "", "1234", "12345", "12345"}
		};
		table = new JTable(contentes, header);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table.addMouseListener(this);
		JScrollPane scrollpane = new JScrollPane(table);
		Main.center.add(scrollpane);
		return Main.center;
	}

	static class mouseEve implements MouseListener{
		public void mouseClicked(MouseEvent e){
			
			int row = table.getSelectedRow();
			TableModel data = table.getModel();
			String text_title = (String)data.getValueAt(row, 1);
			String text = (String)data.getValueAt(row, 2);
			String date = (String)data.getValueAt(row, 3);
			String user_id = (String)data.getValueAt(row, 5);

			JPanel p = new JPanel(new GridLayout(4, 2));

			txtTitle.setText(text_title);
			txtText.setText(text);
			txtData.setText(date);
			txtUserId.setText(user_id);

			p.add(txtTitle);
			p.add(txtText);
			p.add(txtData);
			p.add(txtUserId);

			Main.c.add(p, BorderLayout.CENTER);
			
		}
	}
	
	static class SelectListner implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String selectedType = "";
			if (select_btn.equals(e.getSource())){
				if (weight.isSelected()){
					selectedType = new String("weight");
				} else {
					selectedType = new String("diet");
				}
				System.out.println(selectedType);
				AddWeightDiet(selectedType);
			}
			
		}
	}
}
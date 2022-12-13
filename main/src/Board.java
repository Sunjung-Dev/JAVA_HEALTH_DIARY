

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.*;
import java.util.List;

public class Board extends JFrame{
	static JRadioButton diet = new JRadioButton("DIET");
	static JRadioButton weight = new JRadioButton("WEIGHT"); 
	static JButton select_btn = new JButton("관련게시글 보기");

	public static JPanel Layout() {
		Main.center.add(diet);
		Main.center.add(weight);
		Main.center.add(select_btn);
		select_btn.addActionListener(new SelectListner());
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
			{"1", "hi", "sdf", "123", "df", "123", "32"}
		};
		JTable table = new JTable(contentes, header);
		JScrollPane scrollpane = new JScrollPane(table);
		Main.center.add(scrollpane);
		return Main.center;
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


import java.awt.*;
import javax.swing.*;

public class Layout extends JFrame{
	public Layout() {
		JPanel board =new JPanel();
		Container c = getContentPane();
		c.setLayout(new BorderLayout(30, 20));
		
		JLabel height = new JLabel("키");
		JTextField height_data = new JTextField(20);
		
		board.add(height);
		board.add(height_data);
		
		JLabel weight = new JLabel("몸무게");
		JTextField weight_data = new JTextField(20);
		
		board.add(weight);
		board.add(weight_data);
		
		c.add(board);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(500, 500);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Layout();
	}

}

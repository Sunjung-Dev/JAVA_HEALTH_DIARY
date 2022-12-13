import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import Mypage.CalendarDataManager;

import java.awt.*;
import java.sql.*;

public class Mypage extends JFrame{
    static JRadioButton cal = new JRadioButton("날짜형");
	static JRadioButton list = new JRadioButton("목록형");
	static JButton mypostBtn = new JButton("나의 작성글 확인");
	static JButton newpostBtn = new JButton("식단&운동 일지 작성");
    static JPanel mypage = new JPanel();

    public static JPanel Layout() {
        // center, south 합친거 필요함 jpanel로
        Main.center.add(cal);
		Main.center.add(list);

        Main.south.add(mypostBtn);
        mypostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // new MemoCalendar();
            }
        });

        Main.south.add(newpostBtn);
        newpostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // new MemoCalendar();
            }
        });

        mypage.add(Main.center);
        mypage.add(Main.south);
        return mypage;
    }

    // static public class MemoCalendar extends CalendarDataManager{
        
    // }
    
}

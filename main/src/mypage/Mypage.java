import java.awt.*;
import java.io.*;
import java.net.http.WebSocket.Listener;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mypage extends JFrame {

	ButtonGroup show = new ButtonGroup();
	JButton loginBtn = new JButton("로그인");
	JButton postBtn = new JButton("게시판");
	JButton mypageBtn = new JButton("마이페이지");
	JRadioButton[] Rbtn = new JRadioButton[2];
	JButton newpostBtn = new JButton("식단&운동 일지 작성");
	JFrame mainFrame = new JFrame("Mypage");
	JPanel north = new JPanel();
	JPanel menu = new JPanel();
	JPanel undermenu = new JPanel();
	JPanel center = new JPanel();
	JPanel south = new JPanel();

	JPanel frameSubPanelWest = new JPanel();
	JPanel frameSubPanelEast = new JPanel();
	JPanel frameBottomPanel = new JPanel();

	Boolean choice;
	String type;
	String date;
	String memo;
	int userId;

	public Mypage() {

		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setSize(1000, 700);
		mainFrame.setLayout(new BorderLayout());

		Rbtn[0] = new JRadioButton("날짜형");
		Rbtn[1] = new JRadioButton("목록형");
		MyItemListener lShow = new MyItemListener();
		Rbtn[1].addItemListener(lShow);

		menu.setBackground(Color.GRAY);

		show.add(Rbtn[0]);
		show.add(Rbtn[1]);

		menu.setPreferredSize(new Dimension( 1000,55));
		Dimension menuSize = north.getPreferredSize();
		menuSize.height = 80;
		north.setPreferredSize(menuSize);
		// menu.add(loginBtn);
		// menu.add(postBtn);
		// menu.add(mypageBtn);
		undermenu.add(Rbtn[0]);
		undermenu.add(Rbtn[1]);
		north.setLayout(new BorderLayout());
		// north.add(menu, BorderLayout.NORTH);
		north.add(undermenu, BorderLayout.CENTER);
		south.add(newpostBtn);
		mainFrame.add(north, BorderLayout.NORTH);
		mainFrame.add(south, BorderLayout.SOUTH);
		mainFrame.setVisible(true);

		Rbtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Rbtn[0].isSelected())
					new MemoCalendar();
				frameSubPanelWest.setVisible(true);
				frameSubPanelEast.setVisible(true);
				frameBottomPanel.setVisible(true);
			}
		});

		Rbtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Rbtn[1].isSelected()) {
					frameSubPanelWest.setVisible(false);
					frameSubPanelEast.setVisible(false);
					frameBottomPanel.setVisible(false);

					
					Connection con = Database.makeConnection();
					String sql1 = "SELECT user_num from user WHERE user_name='" + Main.login_completed_name + "'";
					System.out.println(sql1);
					userId = Database.selectUserID(sql1, con);
					System.out.println(userId);
					String sql = "SELECT * FROM board where user_id='" + userId +"'";
					String Mydata[][] = Database.selectBoardTable(sql, con);
					String header[] = {"board_num", "text_title", "text", "datetime", "isOk", "user_id", "type"};
					String contentes[][] = Mydata;

					JTable table = new JTable(contentes, header);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					JScrollPane scrollpane = new JScrollPane(table);
					mainFrame.add(scrollpane);
					
					
				}

			}

			private JLabel JLabel(String string) {
				return null;
			}
		});
		newpostBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MemoCalendar();
				frameSubPanelWest.setVisible(true);
				frameSubPanelEast.setVisible(true);
				frameBottomPanel.setVisible(true);
			}
		});

	}

	class MyItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			if (Rbtn[1].isSelected()) {
				// select * from dept01 where (공개여부)=true;

			}

		}

	}

	class CalendarDataManager { // 6*7�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
		static final int CAL_WIDTH = 7;
		final static int CAL_HEIGHT = 6;
		int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
		int calYear;
		int calMonth;
		int calDayOfMon;
		final int calLastDateOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int calLastDate;
		Calendar today = Calendar.getInstance();
		Calendar cal;

		public CalendarDataManager() {
			setToday();
		}

		public void setToday() {
			calYear = today.get(Calendar.YEAR);
			calMonth = today.get(Calendar.MONTH);
			calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
			makeCalData(today);
		}

		private void makeCalData(Calendar cal) {
			// 1���� ��ġ�� ������ ��¥�� ����
			int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
			if (calMonth == 1)
				calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
			else
				calLastDate = calLastDateOfMonth[calMonth];
			// �޷� �迭 �ʱ�ȭ
			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					calDates[i][j] = 0;
				}
			}
			// �޷� �迭�� �� ä���ֱ�
			for (int i = 0, num = 1, k = 0; i < CAL_HEIGHT; i++) {
				if (i == 0)
					k = calStartingPos;
				else
					k = 0;
				for (int j = k; j < CAL_WIDTH; j++) {
					if (num <= calLastDate)
						calDates[i][j] = num++;
				}
			}
		}

		private int leapCheck(int year) { // �������� Ȯ���ϴ� �Լ�
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				return 1;
			else
				return 0;
		}

		public void moveMonth(int mon) { // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
			calMonth += mon;
			if (calMonth > 11)
				while (calMonth > 11) {
					calYear++;
					calMonth -= 12;
				}
			else if (calMonth < 0)
				while (calMonth < 0) {
					calYear--;
					calMonth += 12;
				}
			cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);
			makeCalData(cal);
		}
	}

	class MemoCalendar extends CalendarDataManager { // CalendarDataManager�� GUI + �޸��� + �ð�
		// â ������ҿ� ��ġ��
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));

		JPanel calOpPanel;
		JButton todayBut;
		JLabel todayLab;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curMMYYYYLab;
		JButton nMonBut;
		JButton nYearBut;
		ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

		JPanel calPanel;
		JButton weekDaysName[];
		JButton dateButs[][] = new JButton[6][7];
		listenForDateButs lForDateButs = new listenForDateButs();

		JPanel infoPanel;
		JLabel infoClock;

		JPanel memoPanel;
		JLabel selectedDate;
		JTextArea memoArea;
		JScrollPane memoAreaSP;
		JPanel memoSubPanel;
		JButton saveBut;
		JButton delBut;
		JButton clearBut;
		ButtonGroup upload = new ButtonGroup();

		JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
		// ���, �޼���
		final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
		
		final String SaveButMsg1 = "�� MemoData������ �����Ͽ����ϴ�.";
		final String SaveButMsg2 = "�޸� ���� �ۼ��� �ּ���.";
		final String SaveButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
		final String DelButMsg1 = "�޸� �����Ͽ����ϴ�.";
		final String DelButMsg2 = "�ۼ����� �ʾҰų� �̹� ������ memo�Դϴ�.";
		final String DelButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
		final String ClrButMsg1 = "�Էµ� �޸� ������ϴ�.";

		public MemoCalendar() { // ������� ������ ���ĵǾ� ����. �� �ǳ� ���̿� ���ٷ� ����

			mainFrame.setLocationRelativeTo(null);
			mainFrame.setResizable(false);
			mainFrame.setIconImage(icon.getImage());
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// LookAndFeel
																								// Windows ��Ÿ��
																								// ����
				SwingUtilities.updateComponentTreeUI(mainFrame);
			} catch (Exception e) {
				bottomInfo.setText("ERROR : LookAndFeel setting failed");
			}

			calOpPanel = new JPanel();
			todayBut = new JButton("Today");
			todayBut.setToolTipText("Today");
			todayBut.addActionListener(lForCalOpButtons);
			todayLab = new JLabel(today.get(Calendar.MONTH) + 1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/"
					+ today.get(Calendar.YEAR));
			lYearBut = new JButton("<<");
			lYearBut.setToolTipText("Previous Year");
			lYearBut.addActionListener(lForCalOpButtons);
			lMonBut = new JButton("<");
			lMonBut.setToolTipText("Previous Month");
			lMonBut.addActionListener(lForCalOpButtons);
			curMMYYYYLab = new JLabel(
					"<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
							+ (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
			nMonBut = new JButton(">");
			nMonBut.setToolTipText("Next Month");
			nMonBut.addActionListener(lForCalOpButtons);
			nYearBut = new JButton(">>");
			nYearBut.setToolTipText("Next Year");
			nYearBut.addActionListener(lForCalOpButtons);
			calOpPanel.setLayout(new GridBagLayout());
			GridBagConstraints calOpGC = new GridBagConstraints();
			calOpGC.gridx = 1;
			calOpGC.gridy = 1;
			calOpGC.gridwidth = 2;
			calOpGC.gridheight = 1;
			calOpGC.weightx = 1;
			calOpGC.weighty = 1;
			calOpGC.insets = new Insets(5, 5, 0, 0);
			calOpGC.anchor = GridBagConstraints.WEST;
			calOpGC.fill = GridBagConstraints.NONE;
			calOpPanel.add(todayBut, calOpGC);
			calOpGC.gridwidth = 3;
			calOpGC.gridx = 2;
			calOpGC.gridy = 1;
			calOpPanel.add(todayLab, calOpGC);
			calOpGC.anchor = GridBagConstraints.CENTER;
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 1;
			calOpGC.gridy = 2;
			calOpPanel.add(lYearBut, calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 2;
			calOpGC.gridy = 2;
			calOpPanel.add(lMonBut, calOpGC);
			calOpGC.gridwidth = 2;
			calOpGC.gridx = 3;
			calOpGC.gridy = 2;
			calOpPanel.add(curMMYYYYLab, calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 5;
			calOpGC.gridy = 2;
			calOpPanel.add(nMonBut, calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 6;
			calOpGC.gridy = 2;
			calOpPanel.add(nYearBut, calOpGC);

			calPanel = new JPanel();
			weekDaysName = new JButton[7];
			for (int i = 0; i < CAL_WIDTH; i++) {
				weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
				weekDaysName[i].setBorderPainted(false);
				weekDaysName[i].setContentAreaFilled(false);
				weekDaysName[i].setForeground(Color.WHITE);
				if (i == 0)
					weekDaysName[i].setBackground(new Color(200, 50, 50));
				else if (i == 6)
					weekDaysName[i].setBackground(new Color(50, 100, 200));
				else
					weekDaysName[i].setBackground(new Color(150, 150, 150));
				weekDaysName[i].setOpaque(true);
				weekDaysName[i].setFocusPainted(false);
				calPanel.add(weekDaysName[i]);
			}
			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					dateButs[i][j] = new JButton();
					dateButs[i][j].setBorderPainted(false);
					dateButs[i][j].setContentAreaFilled(false);
					dateButs[i][j].setBackground(Color.WHITE);
					dateButs[i][j].setOpaque(true);
					dateButs[i][j].addActionListener(lForDateButs);
					calPanel.add(dateButs[i][j]);
				}
			}
			calPanel.setLayout(new GridLayout(0, 7, 2, 2));
			calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			showCal(); // �޷��� ǥ��

			infoPanel = new JPanel();
			infoPanel.setLayout(new BorderLayout());
			infoClock = new JLabel("", SwingConstants.RIGHT);
			infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			infoPanel.add(infoClock, BorderLayout.NORTH);
			selectedDate = new JLabel("<Html><font size=3>" + (today.get(Calendar.MONTH) + 1) + "/"
					+ today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR) + "&nbsp;(Today)</html>",
					SwingConstants.LEFT);

					// today.get(Calendar.DAY_OF_MONTH) 
			// String date = new String((today.get(Calendar.MONTH) + 1) + "/"
			// + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR));

			String date = new String((today.get(Calendar.YEAR) + 1) + "-"
			+ today.get(Calendar.MONTH) + "-" + today.get(Calendar.DAY_OF_MONTH));

			// date = new String(today.get(Calendar.YEAR) + "-" + today.get(Calendar.MONTH) + "-" + today.get(Calendar.DAY_OF_MONTH) );

			selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));


			ButtonGroup Mpublic = new ButtonGroup();
			JRadioButton[] Mbtn = new JRadioButton[2];
			Mbtn[0] = new JRadioButton("Public");
			Mbtn[1] = new JRadioButton("Private");
			Mpublic.add(Mbtn[0]);
			Mpublic.add(Mbtn[1]);
			Mbtn[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Mbtn[0].isSelected()){
						choice = true;
						Mbtn[0].setForeground(Color.RED);
						Mbtn[1].setForeground(Color.BLACK);
					}
				}
			});
			Mbtn[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Mbtn[1].isSelected()){
						choice = false;
						Mbtn[0].setForeground(Color.BLACK);
						Mbtn[1].setForeground(Color.RED);
					}
	
				}
			});

			ButtonGroup Mtype = new ButtonGroup();
			JRadioButton[] Tbtn = new JRadioButton[2];
			Tbtn[0] = new JRadioButton("Weight");
			Tbtn[1] = new JRadioButton("Diet");
			Mtype.add(Tbtn[0]);
			Mtype.add(Tbtn[1]);
			Tbtn[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Tbtn[0].isSelected()){
						type = "Weight";
						Tbtn[0].setForeground(Color.BLUE);
						Tbtn[1].setForeground(Color.BLACK);
					}
				}
			});
			Tbtn[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Tbtn[1].isSelected()){
						type = "Diet";
						Tbtn[0].setForeground(Color.BLACK);
						Tbtn[1].setForeground(Color.BLUE);
					}
	
				}
			});

			memoPanel = new JPanel();
			memoPanel.setBorder(BorderFactory.createTitledBorder("Memo"));
			memoArea = new JTextArea();
			memoArea.setLineWrap(true);
			memoArea.setWrapStyleWord(true);
			memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			readMemo();

			memoSubPanel = new JPanel();
			JPanel memoBottomPanel = new JPanel();
			saveBut = new JButton("Save");
			saveBut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						Connection con = Database.makeConnection();
						// String sql = "SELECT * from user WHERE user_name='" + Main.login_completed_name.toString()+ "'";
						// userId = Database.selectUserID(sql, con);
						// System.out.println(userId);
						File f = new File("MemoData");
						if (!f.isDirectory())
							f.mkdir();

						String memo = memoArea.getText();
						if (memo.length() > 0) {
							BufferedWriter out = new BufferedWriter(new FileWriter(
									"MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
											+ (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt"));
							String str = memoArea.getText();
							out.write(str);
							out.close();
							bottomInfo.setText(calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
									+ (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt" + SaveButMsg1);
						} else
							bottomInfo.setText(SaveButMsg2);
						String sql = "INSERT INTO board(text_title, text, date, public, user_id, type) VALUES (' ', '" + memo + "','" + date + "', "+ choice +", "+userId+",'"+type+"')";
						Database.insert(sql, con);
					} catch (IOException e) {
						bottomInfo.setText(SaveButMsg3);
					}
					showCal();
				}
			});
			delBut = new JButton("Delete");
			delBut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					memoArea.setText("");
					File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
							+ (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt");
					if (f.exists()) {
						f.delete();
						showCal();
						bottomInfo.setText(DelButMsg1);
					} else
						bottomInfo.setText(DelButMsg2);
				}
			});
			clearBut = new JButton("Clear");
			clearBut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					memoArea.setText(null);
					bottomInfo.setText(ClrButMsg1);
				}
			});
			memoSubPanel.add(Mbtn[0]);
			memoSubPanel.add(Mbtn[1]);
			memoSubPanel.add(Tbtn[0]);
			memoSubPanel.add(Tbtn[1]);
			memoBottomPanel.add(saveBut);
			memoBottomPanel.add(delBut);
			memoBottomPanel.add(clearBut);


			Dimension memoBottomPanelSize = memoBottomPanel.getPreferredSize();
			memoBottomPanelSize.height = 50;
			memoBottomPanel.setPreferredSize(memoBottomPanelSize);

			memoPanel.setLayout(new BorderLayout());

			JPanel memoMenu = new JPanel();
			Dimension MmenuSize = memoMenu.getPreferredSize();
			MmenuSize.height = 50;
			memoMenu.setPreferredSize(MmenuSize);

			memoMenu.setLayout(new BorderLayout());
			memoMenu.add(selectedDate, BorderLayout.NORTH);
			memoMenu.add(memoSubPanel,  BorderLayout.CENTER);

			memoPanel.add(memoMenu, BorderLayout.NORTH);
			memoPanel.add(memoAreaSP, BorderLayout.CENTER);
			memoPanel.add(memoBottomPanel, BorderLayout.SOUTH);

			// calOpPanel, calPanel�� frameSubPanelWest�� ��ġ
			Dimension calOpPanelSize = calOpPanel.getPreferredSize();
			calOpPanelSize.height = 90;
			calOpPanelSize.width = 90;
			calOpPanel.setPreferredSize(calOpPanelSize);
			frameSubPanelWest.setLayout(new BorderLayout());
			frameSubPanelWest.add(calOpPanel, BorderLayout.NORTH);
			frameSubPanelWest.add(calPanel, BorderLayout.CENTER);

			// infoPanel, memoPanel�� frameSubPanelEast�� ��ġ
			Dimension infoPanelSize = infoPanel.getPreferredSize();
			infoPanelSize.height = 65;
			infoPanel.setPreferredSize(infoPanelSize);
			frameSubPanelEast.setLayout(new BorderLayout());
			frameSubPanelEast.add(infoPanel, BorderLayout.NORTH);
			frameSubPanelEast.add(memoPanel, BorderLayout.CENTER);

			Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
			frameSubPanelWestSize.width = 610;
			frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

			// �ڴʰ� �߰��� bottom Panel..
			Dimension frameBottomPanelSize = frameBottomPanel.getPreferredSize();
			frameBottomPanelSize.height = 65;
			frameBottomPanel.setPreferredSize(frameBottomPanelSize);
			frameBottomPanel.add(bottomInfo);

			// frame�� ���� ��ġ
			//mainFrame.setLayout(new BorderLayout());
			mainFrame.add(north, BorderLayout.NORTH);
			// north.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
			// center.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
			mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
			mainFrame.add(center, BorderLayout.CENTER);
			mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
			mainFrame.add(south, BorderLayout.SOUTH);
			mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
			mainFrame.setVisible(true);

			focusToday(); // ���� ��¥�� focus�� �� (mainFrame.setVisible(true) ���Ŀ� ��ġ�ؾ���)

			// Thread �۵�(�ð�, bottomMsg �����ð��� ����)
			ThreadConrol threadCnl = new ThreadConrol();
			threadCnl.start();
		}

		private void focusToday() {
			if (today.get(Calendar.DAY_OF_WEEK) == 1)
				dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK) - 1]
						.requestFocusInWindow();
			else
				dateButs[today.get(Calendar.WEEK_OF_MONTH) - 1][today.get(Calendar.DAY_OF_WEEK) - 1]
						.requestFocusInWindow();
		}

		private void readMemo() {
			try {
				File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
						+ (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt");
				if (f.exists()) {
					BufferedReader in = new BufferedReader(
							new FileReader("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
									+ (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt"));
					String memoAreaText = new String();
					while (true) {
						String tempStr = in.readLine();
						if (tempStr == null)
							break;
						memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
					}
					memoArea.setText(memoAreaText);
					in.close();
				} else
					memoArea.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void showCal() {
			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					String fontColor = "black";
					if (j == 0)
						fontColor = "red";
					else if (j == 6)
						fontColor = "blue";

					File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
							+ (calDates[i][j] < 10 ? "0" : "") + calDates[i][j] + ".txt");
					if (f.exists()) {
						dateButs[i][j].setText(
								"<html><b><font color=" + fontColor + ">" + calDates[i][j] + "</font></b></html>");
					} else
						dateButs[i][j].setText(
								"<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");

					JLabel todayMark = new JLabel("<html><font color=green>*</html>");
					dateButs[i][j].removeAll();
					if (calMonth == today.get(Calendar.MONTH) &&
							calYear == today.get(Calendar.YEAR) &&
							calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
						dateButs[i][j].add(todayMark);
						dateButs[i][j].setToolTipText("Today");
					}

					if (calDates[i][j] == 0)
						dateButs[i][j].setVisible(false);
					else
						dateButs[i][j].setVisible(true);
				}
			}
		}

		private class ListenForCalOpButtons implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == todayBut) {
					setToday();
					lForDateButs.actionPerformed(e);
					focusToday();
				} else if (e.getSource() == lYearBut)
					moveMonth(-12);
				else if (e.getSource() == lMonBut)
					moveMonth(-1);
				else if (e.getSource() == nMonBut)
					moveMonth(1);
				else if (e.getSource() == nYearBut)
					moveMonth(12);

				curMMYYYYLab.setText(
						"<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
								+ (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
				showCal();
			}
		}

		private class listenForDateButs implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int k = 0, l = 0;
				for (int i = 0; i < CAL_HEIGHT; i++) {
					for (int j = 0; j < CAL_WIDTH; j++) {
						if (e.getSource() == dateButs[i][j]) {
							k = i;
							l = j;
						}
					}
				}

				if (!(k == 0 && l == 0))
					calDayOfMon = calDates[k][l]; // today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������
													// ���� �κ�

				cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);

				String dDayString = new String();
				int dDay = ((int) ((cal.getTimeInMillis() - today.getTimeInMillis()) / 1000 / 60 / 60 / 24));
				if (dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
						&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
						&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)))
					dDayString = "Today";
				else if (dDay >= 0)
					dDayString = "D-" + (dDay + 1);
				else if (dDay < 0)
					dDayString = "D+" + (dDay) * (-1);

				selectedDate.setText("<Html><font size=3>" + (calMonth + 1) + "/" + calDayOfMon + "/" + calYear
						+ "&nbsp;(" + dDayString + ")</html>");

				date = new String(calYear +"-" + calDayOfMon + "-" + (dDayString + 1));

				readMemo();
			}
		}

		private class ThreadConrol extends Thread {
			public void run() {
				boolean msgCntFlag = false;
				int num = 0;
				String curStr = new String();
				while (true) {
					try {
						today = Calendar.getInstance();
						String amPm = (today.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
						String hour;
						if (today.get(Calendar.HOUR) == 0)
							hour = "12";
						else if (today.get(Calendar.HOUR) == 12)
							hour = " 0";
						else
							hour = (today.get(Calendar.HOUR) < 10 ? " " : "") + today.get(Calendar.HOUR);
						String min = (today.get(Calendar.MINUTE) < 10 ? "0" : "") + today.get(Calendar.MINUTE);
						String sec = (today.get(Calendar.SECOND) < 10 ? "0" : "") + today.get(Calendar.SECOND);
						infoClock.setText(amPm + " " + hour + ":" + min + ":" + sec);

						sleep(1000);
						String infoStr = bottomInfo.getText();

						if (infoStr != " " && (msgCntFlag == false || curStr != infoStr)) {
							num = 5;
							msgCntFlag = true;
							curStr = infoStr;
						} else if (infoStr != " " && msgCntFlag == true) {
							if (num > 0)
								num--;
							else {
								msgCntFlag = false;
								bottomInfo.setText(" ");
							}
						}
					} catch (InterruptedException e) {
						System.out.println("Thread:Error");
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new Mypage();
	}
}
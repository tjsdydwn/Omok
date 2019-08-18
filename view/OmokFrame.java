package omok.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OmokFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -7202540779600149090L;

	private JButton btnStart, btnClear, btnCancel;
	private OmokPan pan;
	private JTextArea status;
	private TimerLbl timer;

	public OmokFrame() {
		Container con = this.getContentPane();
		con.setLayout(null);

		pan = new OmokPan();
		pan.setBounds(15, 35, 601, 601);
		con.add(pan);

		JLabel lblTime = new JLabel("남은시간:");
		lblTime.setBounds(450, 5, 50, 20);
		con.add(lblTime);

		timer = new TimerLbl();
		timer.setBounds(510, 5, 50, 20);
		con.add(timer);

		status = new JTextArea();
		status.setEditable(false);
		JScrollPane scroll = new JScrollPane(status);
		scroll.setBounds(15, 650, 600, 120);
		con.add(scroll);

		btnStart = new JButton("시작");
		btnStart.setBounds(15, 780, 180, 40);
		con.add(btnStart);

		btnClear = new JButton("모두지우기");
		btnClear.setBounds(200, 780, 180, 40);
		con.add(btnClear);

		btnCancel = new JButton("무르기");
		btnCancel.setBounds(385, 780, 180, 40);
		con.add(btnCancel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 850);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		addEvent();
	}

	public void addEvent() {
		btnStart.addActionListener(this);
		btnClear.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnClear) {
			pan.clear();
		} else if (e.getSource() == btnCancel) {
			pan.cancel();
		} else if (e.getSource() == btnStart) {

		}
	}

	public void addMessage(String msg) {
		status.append(msg);
	}
}
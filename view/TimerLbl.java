package omok.view;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class TimerLbl extends JLabel {
	private static final long serialVersionUID = -5141020786753424262L;
	private TimerTask task;
	private Timer timer;
	private int time = 60;

	public TimerLbl() {
		setText("60");
		task = new TimerTask() {
			@Override
			public void run() {
				time--;
				setText("" + time);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		timer = new Timer();
		timer.schedule(task, 1000);
	}
}

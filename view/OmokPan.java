package omok.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OmokPan extends JPanel {
	private static final long serialVersionUID = 5954310059018794006L;
	private ArrayList<OmokDol> omokList;
	private int size = 30;

	private int shadowX;
	private int shadowY;
	private boolean flag;

	public OmokPan() {
		omokList = new ArrayList<OmokDol>();
		flag = true;
		setBackground(new Color(244, 214, 168));
		addEvent();
	}

	public void addEvent() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int x = (int) Math.round(e.getX() / (double) size) * size - 15;
				int y = (int) Math.round(e.getY() / (double) size) * size - 15;

				if (hasPosition(x, y)) {
					return;
				}

				if (isOnEdge(x, y)) {
					return;
				}

				OmokDol dol = new OmokDol(x, y, Dol.BLACK);
				if (flag) {
					omokList.add(dol);
					flag = false;
				} else {
					dol.setColor(Dol.WHITE);
					omokList.add(dol);
					flag = true;
				}

				System.out.println(x + ", " + y);

				repaint();
				if (isWinH(dol)) {
					JOptionPane.showMessageDialog(OmokPan.this, "승리");
					clear();
				}
				if (isWinV(dol)) {
					JOptionPane.showMessageDialog(OmokPan.this, "승리");
					clear();
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// 어느 위치에 바둑돌이 놓일지 알려주기 위해
				// 마우스 이동시 실시간으로 좌표값을 받아옴.
				shadowX = (int) Math.round(e.getX() / (double) size) * size - 15;
				shadowY = (int) Math.round(e.getY() / (double) size) * size - 15;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 바둑판의 줄을 그려줌.
		for (int i = size; i <= 600 - size; i += size) {
			g.drawLine(size, i, 600 - size, i); // 가로줄
			g.drawLine(i, size, i, 600 - size); // 세로줄
		}

		// 리스트에 있는 바둑돌을 모두 꺼내 그려줌
		for (OmokDol dol : omokList) {
			if (dol.getColor() == Dol.BLACK) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			g.fillOval(dol.getX(), dol.getY(), size, size);
		}

		// 바둑돌의 위치를 미리 그려줌
		if (!isOnEdge(shadowX, shadowY)) {
			g.setColor(Color.DARK_GRAY);
			g.drawOval(shadowX, shadowY, size, size);
		}
	}

	// 마우스가 바둑판 가장자리에 있는지 검사
	public boolean isOnEdge(int x, int y) {
		boolean result = true;

		if (x != -15 && x != 585 && y != -15 && y != 585) {
			result = false;
		}

		return result;
	}

	// 바둑돌이 이미 자리에 있는지 확인
	public boolean hasPosition(int x, int y) {
		boolean result = false;
		for (OmokDol dol : omokList) {
			if (x == dol.getX() && y == dol.getY()) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void clear() {
		omokList.clear();
		repaint();
	}

	public void cancel() {
		if (omokList.size() > 0) {
			omokList.remove(omokList.size() - 1);
			repaint();
		}
	}

	public boolean isWinH(OmokDol dol) {
		boolean result = false;
		ArrayList<OmokDol> list = new ArrayList<OmokDol>();

		list.add(dol);
		for (OmokDol omok : omokList) { // 들어온 오목과 같은 색깔만 담음.
			if (omok.getColor() == dol.getColor() && omok.getY() == dol.getY()) {
				list.add(omok);
			}
		}

		if (list.size() == 0) {
			return false;
		}

		int[] arr = new int[list.size()];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).getX();
		}

		int cnt = countDol(arr);

		if (cnt > 4) {
			result = false;
		} else if (cnt == 4) {
			result = true;
		}

		return result;
	}

	public boolean isWinV(OmokDol dol) {
		boolean result = false;
		ArrayList<OmokDol> list = new ArrayList<OmokDol>();

		list.add(dol);
		for (OmokDol omok : omokList) {
			if (omok.getColor() == dol.getColor() && omok.getX() == dol.getX()) {
				list.add(omok);
			}
		}

		if (list.size() == 0) {
			return false;
		}

		int[] arr = new int[list.size()];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).getY();
		}

		int cnt = countDol(arr);

		if (cnt > 4) {
			result = false;
		} else if (cnt == 4) {
			result = true;
		}

		return result;
	}

	public void test() {

	}

	public int countDol(int[] arr) {
		int result = 0;
		Arrays.sort(arr);

		int temp = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] - temp == size) {
				temp = arr[i];
				result++;
			}
		}
		return result;
	}
}
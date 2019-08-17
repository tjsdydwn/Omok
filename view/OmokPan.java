package omok.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OmokPan extends JPanel {
	private static final long serialVersionUID = 5954310059018794006L;
	private ArrayList<OmokDol> omokList; // 오목돌을 저장할 리스트
	private int size = 30; // 바둑판의 사이즈

	private int shadowX, shadowY; // 바둑돌이 놓일 위치를 미리 보여주는 좌표값.
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

				repaint();
				if (isWinH(dol)) {
					JOptionPane.showMessageDialog(OmokPan.this, dol.getColor() == Dol.BLACK ? "흑돌 승리!!!" : "백돌 승리!!!");
					clear();
				}
				if (isWinV(dol)) {
					JOptionPane.showMessageDialog(OmokPan.this, dol.getColor() == Dol.BLACK ? "흑돌 승리!!!" : "백돌 승리!!!");
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

	public void cancel() {
		if (omokList.size() > 0) {
			omokList.remove(omokList.size() - 1);
			repaint();
		}
	}

	public void clear() {
		omokList.clear();
		repaint();
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

	// 마우스가 바둑판 가장자리에 있는지 검사
	public boolean isOnEdge(int x, int y) {
		boolean result = true;

		if (x != -15 && x != 585 && y != -15 && y != 585) {
			result = false;
		}

		return result;
	}

	// 일렬로 세운 돌의 개수를 세어주는 메소드.
	public int countDol(ArrayList<Integer> list) {
		int result = 0;
		Collections.sort(list);
		int temp = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) - temp == size) {
				result++;
			}
			temp = list.get(i);
		}
		return result;
	}

	// 가로가 5개이면 승리.
	public boolean isWinH(OmokDol dol) {
		boolean result = false;
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (OmokDol omok : omokList) {
			if (omok.getColor() == dol.getColor() && omok.getY() == dol.getY()) {
				list.add(omok.getX());
			}
		}

		if (list.size() == 0) {
			return false;
		}

		if (countDol(list) == 4) {
			result = true;
		}

		return result;
	}

	// 세로가 5개이면 승리.
	public boolean isWinV(OmokDol dol) {
		boolean result = false;
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (OmokDol omok : omokList) {
			if (omok.getColor() == dol.getColor() && omok.getX() == dol.getX()) {
				list.add(omok.getY());
			}
		}

		if (list.size() == 0) {
			return false;
		}

		if (countDol(list) == 4) {
			result = true;
		}

		return result;
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
}
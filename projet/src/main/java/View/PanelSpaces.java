package View;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelSpaces {

	public static void leftSpace(JPanel panel, int space, int R, int G, int B, boolean brighter) {
		JPanel spacePanel = new JPanel();
		if (!brighter) {
			spacePanel.setBackground(new Color(R, G, B));

		} else {
			spacePanel.setBackground(new Color(R, G, B).brighter());
		}
		spacePanel.setBorder(new EmptyBorder(0, space, 0, 0));
		panel.add(spacePanel, BorderLayout.WEST);

	}

	public static void rightSpace(JPanel panel, int space, int R, int G, int B, boolean brighter) {
		JPanel spacePanel = new JPanel();
		if (!brighter) {
			spacePanel.setBackground(new Color(R, G, B));

		} else {
			spacePanel.setBackground(new Color(R, G, B).brighter());
		}
		spacePanel.setBorder(new EmptyBorder(0, 0, 0, space));
		panel.add(spacePanel, BorderLayout.EAST);

	}

	public static void bottomSpace(JPanel panel, int space, int R, int G, int B, boolean brighter) {
		JPanel spacePanel = new JPanel();
		if (!brighter) {
			spacePanel.setBackground(new Color(R, G, B));

		} else {
			spacePanel.setBackground(new Color(R, G, B).brighter());
		}
		spacePanel.setBorder(new EmptyBorder(0, 0, space, 0));
		panel.add(spacePanel, BorderLayout.SOUTH);
	}

	public static void topSpace(JPanel panel, int space, int R, int G, int B, boolean brighter) {
		JPanel spacePanel = new JPanel();
		if (!brighter) {
			spacePanel.setBackground(new Color(R, G, B));

		} else {
			spacePanel.setBackground(new Color(R, G, B).brighter());
		}
		spacePanel.setBorder(new EmptyBorder(space, 0, 0, 0));
		panel.add(spacePanel, BorderLayout.NORTH);

	}

	public static void leftSpace(JPanel panel, int space, Color color) {
		{
			JPanel spacePanel = new JPanel();
			spacePanel.setBackground(color);

			spacePanel.setBorder(new EmptyBorder(0, space, 0, 0));
			panel.add(spacePanel, BorderLayout.WEST);
		}

	}

	public static void rightSpace(JPanel panel, int space, Color color) {
		{
			JPanel spacePanel = new JPanel();
			spacePanel.setBackground(color);

			spacePanel.setBorder(new EmptyBorder(0, 0, 0, space));
			panel.add(spacePanel, BorderLayout.EAST);
		}

	}

	public static void bottomSpace(JPanel panel, int space, Color color) {
		{
			JPanel spacePanel = new JPanel();
			spacePanel.setBackground(color);

			spacePanel.setBorder(new EmptyBorder(0, 0, space, 0));
			panel.add(spacePanel, BorderLayout.SOUTH);
		}

	}

	public static void topSpace(JPanel panel, int space, Color color) {
		{
			JPanel spacePanel = new JPanel();
			spacePanel.setBackground(color);
			spacePanel.setBorder(new EmptyBorder(space, 0, 0, 0));
			panel.add(spacePanel, BorderLayout.NORTH);
		}

	}

}

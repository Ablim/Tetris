package view;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private GameArea gameArea = new GameArea();
	
	public MainFrame() {
		
		setResizable(false);
		add(gameArea.getComponent());
		
		setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - getPreferredSize().getHeight() / 2));
		setTitle("Tetris");
		setIconImage(new ImageIcon("icon.jpg").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
